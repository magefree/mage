import socket
import json
import random
serversocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
serversocket.bind(("localhost",5000))
serversocket.listen(1)
(clientsocket, address) = serversocket.accept()
#clientsocket.send(b"2")
def read_bytes(socket,num_bytes):
    read=b""
    while(len(read)<num_bytes):
        read+=clientsocket.recv(num_bytes-len(read))
    return read
def recieve_msg(socket):
    recvlen=read_bytes(socket,8)
    messagelen=int.from_bytes(recvlen,byteorder='big')
    message=read_bytes(socket,messagelen)
    return message
while(True):
    message=recieve_msg(socket)
    parsed=json.loads(message)
    print(parsed)
    ret=random.randrange(parsed['numActions'])
    clientsocket.send(bytes(str(ret),'ascii')+b"\n")