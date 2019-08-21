from selenium import webdriver
import os
import shutil as cp
import sys
from git import Repo

def dirGit():
    dir = 'C:\\GIT\\SOL\\'
    return dir

def pathWeblogic():
    return 'C:\\ambiente\\sw\\Oracle\\Middleware12212\\Oracle_Home\\user_projects\\domains\\cash\\servers\\AdminServer\\tmp\\_WL_user\\sol-base-ear-5.5.0-SNAPSHOT-DEV'

def openBrowser():
    browser = webdriver.Chrome() 
    browser.get('http://localhost:7001/sol')

def find(fileFonte):
    dirs = os.listdir(pathWeblogic())
    for path in dirs:
        dir = os.listdir(pathWeblogic() +'\\'+ path)
        for warDir in dir:
            if warDir == "war":
                warPath = pathWeblogic() +'\\'+ path + '\\' + warDir
                war = os.listdir(warPath)
                for dirPage in war:
                    if (dirPage == 'pages'):
                        pagesPath = warPath + '\\' + dirPage
                        pages = os.listdir(pagesPath)
                        for file in pages:
                            if fileFonte == file:
                                return pagesPath + '\\' + file

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
            fileName = item.split('/')
            fonte = dirGit() + item
            destino = find(fileName[-1])
            cp.copyfile(fonte, destino)
    else:
        print("Nenhum arquivo para realizar deploy!")

copy()
