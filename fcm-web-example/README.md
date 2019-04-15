Firebase Cloud Messaging web Example
===================================

Este exemplo demonstra como:
- Requisitar permissão para enviar notificações para o usuário.
- Receber mensagens FCM usando o Firebase Cloud Messaging com JavaScript SDK.

Introdução
------------

[Leia Sobre Firebase Cloud Messaging](https://firebase.google.com/docs/cloud-messaging/)

Iniciando
---------------

1. Crie um projeto em [Firebase Console](https://console.firebase.google.com).
2. Acesse o painél de administração [Firebase Admin Console](https://console.firebase.google.com/project/[PROJETO_NOME_AQUI]/settings/cloudmessaging/) e solicite a geração de uma par de chaves.
3. Com esta chave edit o arquivo **index.html** adicionando a sua chave ao arquivo. (PROCURE PELO TEXTO [CHAVE_AQUI])
2. Você irá precisar do [Firebase CLI](https://firebase.google.com/docs/cli/) instalado. Se você não o tiver instale com `npm install -g firebase-tools` e então realize sua configuração com `firebase login`.
3. Na linha de comando, execute `firebase use --add` e selecione o projeto Firebase que você criou.
4. Na linha de comando, execute `firebase serve -p 8081` usando o Firebase CLI para um servidor local.
5. Abra [http://localhost:8081](http://localhost:8081) no navegador.
6. Clique no botão **REQUEST PERMISSION** para solicitação permissão de envio de notificações.
7. Use o Instance ID Token(IID Token) para enviar notificações pelo FCM-SERVER.
