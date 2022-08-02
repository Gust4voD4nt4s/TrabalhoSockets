# TrabalhoSockets
# Objetivos
## Implementar um “Chat de notícias” em Java utilizando “Sockets”:
1. O servidor poderá receber conexões de vários clientes e deverá responder a todas elas
simultaneamente. Para que isso ocorra, o servidor deverá tratar as conexões dos
clientes em “threads” separadas. Ao receber uma conexão de um cliente, o servidor
dispara uma “thread” para atender esse cliente e fica aguardando uma nova conexão.
Quando o cliente se conectar ao servidor, deverá informar o assunto sobre o qual deseja
enviar ou receber notícias: Economia, Entretenimento ou Tecnologia.
2. Cada mensagem recebida pelo servidor deverá ser enviada para todos os clientes
conectados para o mesmo assunto, exceto o que enviou a mensagem, no formato
“<Assunto> : <mensagem> (hh:mm)”. Para que isso ocorra, é necessário que todos os
clientes conectados sejam armazenados em um vetor específico para cada assunto no
servidor.
3. Enquanto um cliente estiver digitando uma mensagem, ele poderá receber mensagens
de outros clientes. Para que isso ocorra, é necessário que o cliente mantenha uma
“thread” ativa para esperar a chegada de novas mensagens.
