from selenium import webdriver
import os
import shutil as cp
import sys
from git import Repo

def dirGit():
    return os.path.abspath('C:\\GIT\\SOL\\')

def pathWeblogic():
    return os.path.abspath('C:\\ambiente\\sw\\Oracle\\Middleware12212\\Oracle_Home\\user_projects\\domains\\cash\\servers\\AdminServer\\tmp\\_WL_user\\sol-base-ear-5.5.0-SNAPSHOT-DEV')

def openBrowser():
    browser = webdriver.Chrome() 
    browser.get('http://localhost:7001/sol')

# Percorre todo o diretório do servido para encontrar os arquivos
def findFile(fileFonte):
    files = []
    for pastaAtual, subPasta, arquivos in os.walk(pathWeblogic()):
        files.extend([os.path.join(pastaAtual, arquivo) for arquivo in arquivos if arquivo == fileFonte])
    return files                                

def repo():
    repo = Repo('C:\\GIT\\SOL\\')
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

def copy():
    files = gitDiff()
    if len(files) > 0:
        for item in files:
            pathFile = item.split('/')
            fileName = pathFile[-1]
            fonte = dirGit() + '/' + item
            destino = findFile(fileName)[0]
            try:
                cp.copyfile(fonte, destino)
                print(fileName + " copiado com sucesso")
            except:
                print("Erro ao copiar o arquivo " + fileName)
    else:
        print("Nenhum arquivo para realizar deploy!")

copy()
