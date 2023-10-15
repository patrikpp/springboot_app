"use strict";


document.addEventListener("DOMContentLoaded", (event) => {
    const exchangeRatesSection = document.querySelector("#exchange-rates-section");
    const prevPageBtn = document.querySelector("#prev-page-btn");
    const nextPageBtn = document.querySelector("#next-page-btn");
    let pageNumber = 0, totalPages = 0;
    fetchExchangeRates();

    prevPageBtn.addEventListener("click", (event) => {
        event.preventDefault();
        if (pageNumber > 0) {
           pageNumber--;
           fetchExchangeRates();
        }
     });

    nextPageBtn.addEventListener("click", (event) => {
        event.preventDefault();
        if (pageNumber < (totalPages - 1)) {
            pageNumber++;
            fetchExchangeRates();
        }
    });

    async function fetchExchangeRates() {
        try {
            const response = await fetch(
                `http://localhost:8080/exchangerates/?usedb=true&page=${pageNumber}`
            );
            
            const responseJson = await response.json();
            totalPages = responseJson.totalPages;
            exchangeRatesSection.innerHTML = "";
        
            responseJson.content.forEach((exchangeRate) => {
                const idStr = htmlEncode(exchangeRate.id);
                const countryStr = htmlEncode(exchangeRate.country);
                const nameStr = htmlEncode(exchangeRate.name);
                exchangeRatesSection.innerHTML += 
                    `<tr>
                        <td>${countryStr}</td>
                        <td>${nameStr}</td>
                        <td><button type="button" class="btn btn-light details-btn" data-id="${idStr}" data-bs-toggle="modal" data-bs-target="#modal-window">More details</button></td> 
                    </tr>`;
            });

            const detailsBtns = document.querySelectorAll(".details-btn");
            detailsBtns.forEach((detailsBtn) => {
                detailsBtn.addEventListener("click", (event) => {
                    event.preventDefault();
                    const exchangeRate = responseJson.content.find(exchangeRate => exchangeRate.id === Number(detailsBtn.dataset.id));
                    document.querySelector("#country-and-name").textContent = exchangeRate.country + " - " + exchangeRate.name;
                    document.querySelector("#short-name").textContent = "shortName: " + exchangeRate.shortName;
                    document.querySelector("#valid-from").textContent = "validFrom: " + exchangeRate.validFrom;
                    document.querySelector("#move").textContent = "move: " + exchangeRate.move;
                    document.querySelector("#amount").textContent = "amount: " + exchangeRate.amount;
                    document.querySelector("#val-buy").textContent = "valBuy: " + exchangeRate.valBuy;
                    document.querySelector("#val-sell").textContent = "valSell: " + exchangeRate.valSell;
                    document.querySelector("#val-mid").textContent = "valMid: " + exchangeRate.valMid;
                    document.querySelector("#curr-buy").textContent = "currBuy: " + exchangeRate.currBuy;
                    document.querySelector("#curr-sell").textContent = "currSell: " + exchangeRate.currSell;
                    document.querySelector("#curr-mid").textContent = "currMid: " + exchangeRate.currMid;
                    document.querySelector("#version").textContent = "version: " + exchangeRate.version;
                    document.querySelector("#cnb-mid").textContent = "cnbMid: " + exchangeRate.cnbMid;
                    document.querySelector("#ecb-mid").textContent = "ecbMid: " + exchangeRate.ecbMid;
                });
            });
        } catch (error) {
            exchangeRatesSection.textContent = "Uh Oh!!! Something went wrong at our end.";
        }
    }   
    
    function htmlEncode(str) {
        return String(str).replace(/[^\w. ]/gi, function(c) {
            return '&#' + c.charCodeAt(0) + ';';
        });
    }
});