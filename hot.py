from selenium import webdriver
import os
import shutil as cp
import sys
from git import Repo
import re
import helpers
import sys

def getEAR():
    path = os.path.abspath(helpers.pathWeblogic()+helpers.separadorDir()+'upload')
    diretorio = os.listdir(path)
    
    for d in diretorio:
        if re.search('base', d, re.IGNORECASE):
            return d

def getDirFilesEAR():
    return helpers.pathWeblogic() + helpers.separadorDir() + 'tmp\\_WL_user' + helpers.separadorDir() + getEAR()            

def openBrowser():
    browser = webdriver.Chrome() 
    browser.get('http://localhost:7001/sol/faces/pages/')

# Percorre todo o diretório do servido para encontrar os arquivos
def findFile(fileFonte):
    files = []
    for pastaAtual, subPasta, arquivos in os.walk(getDirFilesEAR()):
        files.extend([os.path.join(pastaAtual, arquivo) for arquivo in arquivos if arquivo == fileFonte])
    return files                                

def repo():
    repo = Repo(helpers.dirGit())
    assert not repo.bare
    return repo

def gitDiff():
    repositorio = repo()
    itensDiff = []
    for item in repositorio.index.diff(None):
        arr_item = item.a_path.split('.')
        if arr_item[1] == 'xhtml':
            itensDiff.append(item.a_path)
    return itensDiff

def copy(files):
    if files != None:
        files = gitDiff()

    if len(files) > 0:
        for item in files:
            pathFile = item.split('/')
            fileName = pathFile[-1]
            fonte = helpers.dirGit() + '/' + item
            destino = findFile(fileName)[0]
            try:
                cp.copyfile(fonte, destino)
                print(fileName + " copiado com sucesso")
            except:
                print("Erro ao copiar o arquivo " + fileName)
    else:
        print("Nenhum arquivo para realizar deploy!")

def main():
    files = []
    i = 0
    for arg in sys.argv:
        if i != 0:
            files.append(arg)
        i = i + 1
    copy(files)


main()
