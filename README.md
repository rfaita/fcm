# FCM Component

Este componente possui o intuito de gerênciar chamadas ao FCM(Firebase cloud message).

## Requisitos

- jdk 8 instalado
- maven instalado
- docker instalado
- docker-compose instalado

## Utilização

Para utilização realize a criação dentro no seu diretório de usuário do arquivo: **serviceAccountKey.json**

Obs.: Obtenha o arquivo em 
[Firebase Admin Console](https://console.firebase.google.com/project/[BPROJETO_NOME_AQUI]/settings/serviceaccounts/adminsdk) criando em "Gerar nova chave privada".

Exemplo de arquivo:

```json
{
  "type": "service_account",
  "project_id": "teste",
  "private_key_id": "chave private",
  "private_key": "teste",
  "client_email": "teste",
  "client_id": "114192801237704019827",
  "auth_uri": "https://accounts.google.com/o/oauth2/auth",
  "token_uri": "https://accounts.google.com/o/oauth2/token",
  "auth_provider_x509_cert_url": "https://www.googleapis.com/oauth2/v1/certs",
  "client_x509_cert_url": "https://www.googleapis.com/robot/v1/metadata/x509/firebase-admiasdnsdk-y9q5d%40rfaitatessdte.iam.gserviceaccount.com"
}

```

Além disso configure o arquivo: **application.yml** 
dentro do projeto ajustando a propriedade: 
**fcm.firebase**

```yml

fcm:
  firebase:
    dryRun: false
    app:
      url: <url aqui>
    path:
      serviceAccountKey: ${HOME}/serviceAccountKey.json

```

Após isso feito, compile o projeto:

```sh
mvn clean install
```

E inicie o mesmo com o comando:

```sh
docker-compose up --build
```

O acesso e feito pela porta 8080, exemplo: http://localhost:8080

Acesse o endereço http://localhost:8080/swagger-ui.html para explorar os serviços da API.
