# Notater til ferdigstilling:
 - Admin funksjonalitet for å opprette, avslutte og trekke lotteriene måtte blitt implementert
 - Lag login system basert på hva enn Experis bruker (Okta, Azure AD)
 - Sikre endepunkter basert på rolle
 - Legg på Flyway
 - Bytt ut JPA med JDBC Template
   - Splitt opp funksjonaliteten for hele lotterier og enkeltbilletter 

  
# Notater om oppgaven  
Hvert lotteri har 100 nummererte lodd, og kan ha flere viner. De dyreste vinene blir loddet ut sist.
 - Opprett et lotteriobjekt med en liste lodd og en liste viner.
 - Lodd har nummer 1 - 100, og et brukernavn på hvem som har kjøpt det
 - Viner har pris og navn
 - Lotteri går fra dato til dato, så det kan være flere aktive lotterier samtidig

Hver person kjøper et eller flere lodd til 10 kroner.
 - Mock innlogging og betaling

Flyt: Bruker skal kjøpe lotteribillett

1. Bruker velger lotteri
2. Bruker får opp liste med ledige billetter
3. Bruker velger ledig billett og kjøper
4. Bruker får kvittering på kjøpt billett

Flyt: Admin opprettet lotteri

1. Bruker klikker "Opprett lotteri"
2. Bruker velger start og sluttdato
3. Bruker legger til viner

Flyt: Admin avslutter lotteri

1. Bruker henter liste med lotterier
2. Bruker avslutter aktivt lotteri, hvor vinner(e) blir trukket
3. Vinnerne blir varslet om at de har vunnet