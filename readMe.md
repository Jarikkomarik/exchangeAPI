# API Documentation

## Required Environment Variables

Ensure the following environment variables are set before starting the server:

- `AMDOREN_API_KEY`
- `GOOGLE_CLIENT_ID`
- `GOOGLE_CLIENT_SECRET`

**Note:** The server is secured with Google OAuth2 and requires valid credentials to access endpoints.

## Endpoints

1. **Supported Rates**
    - **Endpoint:** `/supported-rates`
    - **Method:** `GET`
    - **Response:**
      ```json
      [
        "CZK:PHP",
        "CZK:HUF",
        "CZK:IDR",
        "CZK:TRY",
        "CZK:HKD",
        "CZK:ISK",
        "CZK:EUR",
        "CZK:DKK",
        "CZK:INR",
        "CZK:ZAR",
        "CZK:ILS",
        "CZK:KRW",
        "CZK:CNY",
        "CZK:THB",
        "CZK:AUD",
        "CZK:PLN",
        "CZK:GBP",
        "CZK:JPY",
        "CZK:NZD",
        "CZK:SEK",
        "CZK:BRL",
        "CZK:CHF",
        "CZK:MXN",
        "CZK:CAD",
        "CZK:BGN",
        "CZK:MYR",
        "CZK:XDR",
        "CZK:USD",
        "CZK:NOK",
        "CZK:SGD",
        "CZK:RON"
      ]
      ```

2. **Rates Comparison**
    - **Endpoint:** `/rates-comparison`
    - **Method:** `GET`
    - **Query Parameters:**
        - `firstRate`: Base currency code (e.g., `CZK`)
        - `secondRate`: Target currency code (e.g., `EUR`)
    - **Response:**
      ```json
      {
        "firstCurrency": "CZK",
        "secondCurrency": "EUR",
        "cnbRate": 25.215,
        "amdorenRate": 25.22227,
        "cnbToAmdorenDiff": -0.00727,
        "errorMessage": ""
      }
      ```

3. **Health Check**
    - **Endpoint:** `/actuator/health`
    - **Method:** `GET`
    - **Response:**
      ```json
      {
        "status": "UP"
      }
      ```

**TODO:** Replace `localhost:8080` with the actual host URL in production. Ensure that you use HTTPS for secure communication.
