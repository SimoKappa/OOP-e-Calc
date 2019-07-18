
# OOP-e-Calc
Progetto di OOP <br/>
# Descrizione delle implementazioni
## Statistiche
Le statistiche vengono estratte tramite le seguenti rotte nel controller.
![rotta per le statistiche](https://user-images.githubusercontent.com/49287565/61211095-dd869700-a6fe-11e9-9b3f-67d73452717e.png)
<br>
In questo modo, vengono differenziati gli accessi alle statistiche numeriche o per la conta delle occorrenze di Stringhe.
A seconda della rotta e del parametro passato, dunque, vengono attivati diversi metodi che si occupano di costruire una lista con oggetti di tipo StatsNum (nel caso in cui la richiesta specifichi attributi di tipo numerico) e StatsStr + "Attributo" (nel caso in cui la richiesta specifichi attributi di tipo Stringa).
La lista viene dunque restituita al controller e resa disponibile all'utente.<br/>
![creazione oggetto statistiche numeri](https://user-images.githubusercontent.com/49287565/61211350-ab296980-a6ff-11e9-9ca4-f897fce7c8cf.png)<br/>
Per le stringhe, invece.<br/>
![creazione oggetti statistiche Stringa](https://user-images.githubusercontent.com/49287565/61211436-dc099e80-a6ff-11e9-817f-1ddd6f1f78a4.png)<br/>


## Filtri
spiegare come sono stati implementati i filtri

# Diagrammi
**Use case diagram:**
![Schermata da 2019-07-14 12-58-50](https://user-images.githubusercontent.com/48387175/61182696-3d196f80-a637-11e9-8cee-84bcb2064056.png)<br/>

**Sequence diagrams:**
![defaultmethod](https://user-images.githubusercontent.com/48387175/61182774-5969dc00-a638-11e9-9a19-bca44cbe8365.png)<br/>
![retrieverep](https://user-images.githubusercontent.com/48387175/61182775-68e92500-a638-11e9-939c-3315b6d1aedf.png)<br/>
![getmetadata](https://user-images.githubusercontent.com/48387175/61182785-92a24c00-a638-11e9-90b3-6380e3ac110d.png)<br/>
![reportStats](https://user-images.githubusercontent.com/48387175/61182788-99c95a00-a638-11e9-8c06-74f0ed5aeead.png)<br/>
![reportStatsStr](https://user-images.githubusercontent.com/48387175/61182794-a2ba2b80-a638-11e9-8b99-7b5070cf4eaf.png)<br/>
![reportsFiltered SD](https://user-images.githubusercontent.com/48387175/61182796-aa79d000-a638-11e9-8bbf-c354e4172966.jpg)<br/>

**Class Diagram**
![DiagrammaClassi ](https://user-images.githubusercontent.com/49287565/61478293-851af800-a991-11e9-8857-bb952809deb2.jpg)<br/>

# Test
- **Download del file e importazione del dataset:** alla chiamata della rotta **localhost:8080/** viene avviato il processo che controlla se il file **csvfile.csv** è presente nella cartella del progetto.
Nel caso questo non sia presente viene scaricato e viene importato il dataset.
Nel caso il file fosse già presente si procede con la creazione del dataset.<br/>
**NOTA:** vengono gestiti casi in cui nel file ci siano dati in forma errata.
![DownloadEImportazione](https://user-images.githubusercontent.com/49287565/61208116-f3905980-a6f6-11e9-9563-c24e9a2c7ea3.png) <br/>
- **Restituzione dei dati:** i dati vengono restituiti con la chiamata tramite metodo GET alla rotta **reports/all** .<br/>
![visualizzaDati](https://user-images.githubusercontent.com/49287565/61208576-06575e00-a6f8-11e9-9786-e44cd02553ed.png)<br/>
- **Estrazione delle statistiche:** possono essere estratte statistiche sui dati presenti, queste differiscono dal tipo di attributi che i dati presentano.
> Sugli attributi di tipo **numerico**, tramite la rotta **reports/stats/num/(value, nca, extraction)**, è possibile ricavare:
valore medio, valore minimo, valore massimo, deviazione standard, somma di tutti i valori, e numero degli elementi; rispettivamente per gli attributi value, nca o extraction, specificati nella rotta.<br/>
![statistiche numeriche](https://user-images.githubusercontent.com/49287565/61208756-68b05e80-a6f8-11e9-857b-bfa8a1409be2.png)<br/>
Sugli attributi di tipo **Stringa**, tramite la rotta **reports/stats/str/(country, period, item, code)**, è possibile ricavare la conta delle occorrenze per ogni elemento unico presente nel dataset relativo all'attributo specificato nella rotta.<br/>
![statistiche sulle stringhe](https://user-images.githubusercontent.com/49287565/61208953-d197d680-a6f8-11e9-850e-f08a9af8000d.png)<br/>
- **Estrazione metadati:** possono essere estratti i metadati tramite la chiamata alla rotta **reports/metadata**. Nell'immagine sono riportati alcuni degli attributi con relativa descrizione e tipo di dato.<br/>
![metadata](https://user-images.githubusercontent.com/49287565/61209677-b1691700-a6fa-11e9-86da-00b0d4b5a675.png)<br/>
- **Estrazione dei dati filtrati:** i dati possono essere filtrati utilizzando la rotta **reports/filtered** con il metodo **POST** specificando il filtro con la sintassi **JSON**.  Questi possono essere di tipo **logico** o **condizionale**.
> I filtri logici previsti sono **\$and** (estrae dati che soddisfano le specifiche di uno dei due parametri) o **\$or** (estrae dati che soddisfano le specifiche di entrambi i parametri) utilizzato nella foto.<br/>
![filtri logici](https://user-images.githubusercontent.com/49287565/61210136-d8741880-a6fb-11e9-8b8e-1368fe1db733.png)<br/>
I filtri condizionali sono **\$lt** (vengono estratti i dati il cui attributo specificato è minore del valore indicato) o **\$gt** (vengono estratti i dati il cui attributo specificato è minore del valore indicato).<br/>
![filtro condizionale](https://user-images.githubusercontent.com/49287565/61210676-86cc8d80-a6fd-11e9-9461-e9101103799b.png)<br/>
**NOTA:** vengono gestiti i casi di sintassi del filtro errata o attributo errato.
