#Background i punkt startowy
\label{sec:start}
W listopadzie/grudniu 2017 przeprowadzono probe analizy wrazliwosci modelu/solwera 
 wykorzystywanego do symulacji wzrostu Tumora w zależnosci od przyjetych parametrow.
 
 Po drobnych doustaleniach jako referencyjne przyjeto nastepujace wywoolanie/zestaw parametrow solwera:
 `./tumor 2 80 10000 0.1 1000 0.5 10 2 10 100 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4`
 
 Zas dla poszczegolnych parametrow przyjeto w analizie nastepujacy zakres ich zmiennosci:
 
 
 * $p_1=2$ -- stopien splajnów - nie zmieniany
 * $p_2=80$ -- ilosc elementów nie zmieniany
 * $p_3=10000$ -- liczba kroków czasowych nie zmieniany
 * $p_4=0.1$ -- wielkosc kroku czasowego nie zmieniany
 * $p_5=1000$ -- co ile krokow zapisywac wynik nie zmieniany
 * $p_6=\tau_B = 0.5 \pm 10\%$ 
 * $p_7=o^{prol} = 10 \pm 10\%$
 * $p_8=o^{death} = 2 \pm 10\%$
 * $p_9=T^{prol} = 10 \pm 10\%$
 * $p_{10}=T^{death} = 100 \pm 10\%$
 * $p_{11}=P_b = 0.001 \pm 10\%$
 * $p_{12}=r_b$ -- zmieniany w zakresie $(0.00001,3/10]$
 * $p_{13}=\beta_M = 0.625 \pm 10\%$ 
 * $p_{14}=\gamma_A = 0.3205 \pm 10\%$
 * $p_{15}=\chi_{oA} = 0.0064 \pm 10\%$
 * $p_{16}=\gamma_{oA} = 0.0064 \pm 10\%$
 * $p_{17}=\chi_c = 0.0000555 \pm 10\%$
 * $p_{18}=\gamma_c = 0.01 \pm 10\%$
 * $p_{19}=\alpha_0 = 0.0000555 \pm \textbf{50\%}$ 
 * $p_{20}=\gamma_T = 0.01 \pm \textbf{50\%}$ 
 * $p_{21}=\alpha_1 = 0.4 \pm \textbf{50\%}$
 
 Podczas obliczen kazdy z wyzej wymienionych zakresow zmiennosci 
 dzielono na 20 rownych kawalkow i startujac od wywolania referencyjnego 
 stopniowo podstawiano kolejna z 21 wartosci parametru w zdefiniowanym zakresie zmiennosci.
 
 A zatem kolejne wywolania solwera dla parametru $p_6$ definiowane byly jako:
 
 * ./tumor 2 80 10000 0.1 1000 \textbf{0.45} 10 2 10 100 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
 * ./tumor 2 80 10000 0.1 1000 \textbf{0.455} 10 2 10 100 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4 
 * ...
 * ./tumor 2 80 10000 0.1 1000 \textbf{0.55} 10 2 10 100 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
 
 Nastepnie powtarzano procedure dla kolejnego parametru powracajac z wartoscia wlasnie analizowanego parametru do wartosci referencyjnej ($0.5$ w powyzszym przypadku).
 
 Uzyskane wyniki, oraz ich wstepna "obróbka" zebrana zostala w arkuszu dostepnym **[Tutaj](http://home.agh.edu.pl/~siwik/tumor/work.ods)**.
 
 Na pierwszy rzut oka, w uzyskanych wynikach w mojej opini nie widac specjalnie sillnych zaleznosci, niemniej 
 daje sie zidentyfikowac parametry ktorych zmiana wartosci zdaje sie miec istotny
 "kierunkowy" wplyw na zachowanie modelu i wyniki uzyskiwane z implementujacego go solwera.

W szczegolności jako parametry majace obserwowalny wplyw na wielkosc tumora 
wyselekcjonowano parametry $p_7=o^{prol}$, $p_8=o^{death}$, $p_9=T^{prol}$, $p_{10}=T^{death}$ a zidentyfikowana zaleznosc 
pomiedzy wartosciami tych parametrow (w przyjetym zakresie zmiennosci) a objetoscia 
guza zobrazowano na ponizszym wykresie. 

![Zwiazek pomiedzy wartosciami parametrow $p_7$, $p_8$, $p_9$ i $p_{10}$ a objetoscia guza](figures/1.png){width=80%}
 

#Definicja problemu
 Jako wstepny problem weryfikujacy mozliwosc dalszego researchu
 bazując na obserwacjach i wynikach skomentowanych w poprzednim paragrafie 
 zdefiniowano problem odwrotny polegajacy na poszukiwaniu
 takich wartosci parametrow $p_7$, $p_8$, $p_9$ i $p_{10}$ aby minimalizowac
 odchylke uzyskiwanych wartosci objetosci guza od objetosci guza uzyskanej dla
 symulacji referencyjnej. 
 
 Ta referencyjna wartość objetosci guza uzyskana podczas pierwszej fazy
 eksperymentow to 402000. A zatem funkcja fitnes definiowana zostala jako: 
 
 \begin{equation}
abs(402000-tumor_{volume})
 \end{equation} 
 
 Wartość ta byla minimalizowana podczas ewolucji.
 

#Użyty algorytm
\label{sec:algorytm}

##Algorytm
Użyty w eksperymentach algorytm to klasyczny algorytm ewolucyjny, spięty z solwerem
symulującym rozrost guza działający zgodnie z przepisem jak poniżej: 

```java
public void run() {
    List<S> offspringPopulation;
    List<S> matingPopulation;

    population = createInitialPopulation();
    population = evaluatePopulation(population);
    initProgress();
    while (!isStoppingConditionReached()) {
      matingPopulation = selection(population);
      offspringPopulation = reproduction(matingPopulation);
      offspringPopulation = evaluatePopulation(offspringPopulation);
      population = replacement(population, offspringPopulation);
      updateProgress();
    }
}
```

##Operatory wariacyjne 
Użyty algorytm wykorzystuje
 
* \textbf{Simulated Binary Crossover (SBXCrossover)}
\footnote{Szczegoly np. tutaj: \url{https://pdfs.semanticscholar.org/b8ee/6b68520ae0291075cb1408046a7dff9dd9ad.pdf}} jako operator krzyzowania
```java 
crossoverOperator = new SBXCrossover(0.9,20);  
```
* \textbf{mutacje wielomianowa} jako operator mutacji z nastepujacymi parametrami:
```java  
mutationOperator = new PolynomialMutation(1.0 / problem.getNumberOfVariables(),20);
```
* oraz \textbf{selekcje turniejowa} jako operator selekcji.




##Zrównoleglenie algorytmu/solwera

Z uwagi na czas wykonania solwera na etapie 
ewaluacji osobników zrównoleglono wywołania solwera. 

Tzn. sam algorytm ewolucyjny 
nie był zrównoleglany w tym sensie, że nie ma tam wielu przetwarzanych
 jednocześnie (sub)populacji etc. Mamy tam "klasyczną", pojedynczą globalnie przetwarzaną populację.
 

Natomiast zastosowano zrównoleglenie na etapie ewaluacji osobników. Tzn 
dla każdego osbnika, dla którego potrzebna jest jego ewaluacja, uruchamiany jest osobny 
proces/watek w którym wykonywany jest kod solwera. 

Zrownoleglewnie realizowane jest poprzez wywolania mpi:
```java
            command = "mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 "
                    + p7 + " " + p8 + " " + p9 + " " + p10
                    + " 0.001 0.3 0.625 0.3205"
                    + " 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333"
                    + " 10 0.003 2 5 25 24 0.003 0.4";

            p = Runtime.getRuntime().exec(
                    command);
``` 


#Wstępny eksperyment


##Parametry algorytmu

 Poniewaz natknieto sie na problemy z czasem wykonania pojedynczej instancji 
 solwera, w pierwszym opisywanym eksperymencie ograniczono obliczenia 
 do 100 ewaluacji (100 uruchomien solwera).
 
 Rozmiar populacji algorytmu przyjęto na 20 (a zatem na etapie ewaluacji uruchamiano max 20 procesów mpi wykonujących kod
 solwera.)
 
 
 
##Parametry modelu

 Zakres zmienności parametrów (ich dopuszczalne granice) 
 zdefiniowane zostaly tak samo jak to miało miejsce przy analizie wrażliwosci modelu, tzn: 
   
 
 * $p_7=o^{prol} \in [9.0,11.0]$ (war ref. 10.0)
 * $p_8=o^{death} \in [1.8,2.2]$ (war ref. 2.0)
 * $p_9=T^{prol} \in [9.0,11.0]$ (war ref. 10.0)
 * $p_{10}=T^{death} \in [90.0,110.0]$ (war ref. 100.0)
 
##Uzyskane wyniki

![](figures/Tv1.png){width=50%}
![](figures/p71.png){width=50%}
![](figures/p81.png){width=50%}
![](figures/p91.png){width=50%}
![](figures/p101.png){width=50%}

![Results in first experiment\label{fig:first}](figures/t.png)

##Napotkane problemy 

Poniżej opisano problem na który nadziano się podczas wywoływania solwera wraz z próbą wyjaśnienia jego przyczyn.
Byc moze przyda sie w analizie/poprawkach modelu/solvera ew. przyszłym użytkownikom zaoszczędzi troche czasu :) 

Klopot na jaki natrafiono to potezny wzrost czasu pojedynczego wykonania 
solwera przy niektorych zestawach parametrow wejsciowych.

Jak bardzo to wydluazalo obliczenia: np w "normalnych" warunkach pojedyncze wywolanie solwera trwalo ok 100s 
o tyle takie "nienormalne" wydluzalo sie np do 2500s albo w ogole sie nie konczylo bo powodowalo
tak duza alokacje pamieci ze bylo ubijane przez mpiruna.

M.in to bylo powodem wyczerpywania zasobow na wezle atari. Natrafiono na to poniewaz 
poczatkowa konfiguracja alg ewolucyjnego zakladala wieksza zmiennosc / szersze zakresy wartosci dopuszczalnych 
poszczegolnych parametrow (zalozono np wstepnie zmiennosc $p_7 \in [5,15]$, $p_8 \in [1,3]$, $p_9 \in [5,15]$ i $p_{10} \in [50,150]$)


Przykladowe wywolanie ktore bardzo dlugo sie liczylo to np:

`../TumorGenetyka/tumor 2 80 10000 0.1 1000 0.5 5.819810396051385 3.4400377040438777 11.112855386932164 125.35805356351595 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4`

Po przyjrzeniu sie temu przypadkowi diagnoza jest taka ze to niefortunne złożenie wartości parametrów.
Parametry 7 - 10 to odpowiednio:
7 - ile co najmniej tlenu potrzeba, żeby komórki się mnożyły
8 - ile co najwyżej musi tlenu być, żeby żyły
9 - jak wolno się mnożą (większe - wolniej)
10 - jak wolno umierają

Uruchomienie które się nie doliczyło ma bardzo niską w porównaniu z resztą wartość 7, czyli 
rak nie potrzebuje bardzo 
dużo tlenu żeby żyć, więc będzie się rozrastał swobodnie.
Jednocześnie ma bardzo niską watość 8, czyli musi mieć dość dużo tlenu żeby 
nie umierać - tam gdzie jest za mało, jest tworzona substancja powodująca 
rozrost żył.

Różnica między tymi dwoma wartościami w tym uruchomieniu jest bardzo mała 
(5.8 - 3.4 vs np. 14 - 1 w drugim), więc jest spora szansa że jak rak 
będzie wołał o tlen to dostanie go od razu na tyle dużo, żeby móc się 
rozrastać.
Jednocześnie parametr 10 jest stosunkowo duży, więc nawet jak nie dostanie 
tlenu od razu to nie będą komórki umierać bardzo szybko.

Czyli podsumowując:

* rak będzie rosnąć jak dostanie nawet niewiele tlenu - rozejdzie się łatwo na większość dziedziny
* jest "wymagający", będzie krzyczał o tlen (= rozrost żył) dopóki nie będzie miał prawie tyle żeby móc rosnąć, więc spowoduje gęste żyły
* nie umiera szybko bez tlenu, więc może poczekać aż te żyły się utworzą

Teraz jakie z tego praktyczne wnioski... trudno powiedzieć coś 
o konkretnych zakresach parametrów. 
Być może należałoby je nieco zawęzić (7 i 8 raczej będą najważniejsze), 
być może jakieś ograniczenia zakresu na podstawie wartości jednego 
z nich (np. żeby różnica między 7 a 8 nie była za duża) acz to chyba
 ciężko byłoby zrobić od strony algorytmu genetycznego. Ale może to 
 nie będzie potrzebne jak się zrobi to co jest opisane dalej.

W przypadkach które się doliczyły też dość sporo się tych naczyń tworzy, 
dużo więcej niż się spodziewałem. Jedna rzecz jaką na szybko można 
zmienić która na pewno przyspieszy znacznie symulację w tej fazie 
z gęstymi naczyniami to usunięcie łączenia naczyń które rosną tak, 
że przez siebie przechodzą, bo to jest zaimplementowane z myślą 
o małych ilościach naczyń i poszukuje bliskich węzłów brutalnie
 kwadratowo. 
 Nie powinno to raczej mieć specjalnie dużego wpływu na wyniki 
 (tak mi się wydaje).

Żeby to zrobić wystarczy zakomentować linijki 187-192 w tumor/vasculature/vasculature.hpp:

                    auto neighbor = find_neighbor(new_tip, tip, cfg.segment_length);
                    if (neighbor != nullptr) {
                        connect(neighbor, new_tip);
                        removed = true;
                        it = sprouts.erase(it);
                    }
 
Co zostalo zrobione i wyniki z tego pierwsego eksperymentu mialy to w solwerze zakomentowane.

Zeby zobrazować problem (być moze naprowadzić na rozwiazanie) ponizej 
czasy wykonania solwera, ilosc segmentow i wezlow zyl, objetosc/masa guza dla: 

* przypadku normalnego (brak nadmiernego rozrostu zyl)
``../TumorGenetyka/tumor 2 80 10000 0.1 1000 0.5 14.449578938363844 1.3853073609726758 7.860871658614398 63.030234570317525 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4``
* przypadku pesymistycznego (nadmierny rozrost)
``../TumorGenetyka/tumor 2 80 10000 0.1 1000 0.5 5.819810396051385 3.4400377040438777 11.112855386932164 125.35805356351595 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4``


![](figures/SolvTime1.png){width=50%}
![](figures/VascNodes1.png){width=50%}
![](figures/VascSeg1.png){width=50%}
![](figures/Tv2.png){width=50%}

![Czas obliczen, siatka zyl, masa guza w "normalnym" i "pesymistycznym" przebiegu symulacji\label{fig:first}](figures/t.png)





#Ewolucyjne poszukiwanie wartości referencyjnych wybranych parametrow startując z 'przypadkowego' zakresu ich wartosci    

Po opisanym wcześniej wstępnym zweryfikowaniu działania narzędzi -  przystapiono
do przeprowadzenia eksperymentu polegającego w uproszczeniu na zweryfikowaniu zdolności odnalezienia 
przez wczesniej opisany solwer (hybryda alg. ewolucyjnego z solwerem modelujacym wzrost guza)  
referencyjnych wartości wybranych parametrów startując z innego zakresu ich wartości. 

Podczas eksperymentu stopniowo zwiększamy 'stopień trudności' tzn zaburzeniu ulega stopniowo (w stosunku do zakresu referencyjnego) rosnąca ilość parametrów 
(tych przyjętych na wstępnym etapie weryfikacji jako istotne czyli odpowiednio:  $p_7=o^{prol}$, $p_8=o^{death}$,  $p_9=T^{prol}$ oraz $p_{10}=T^{death}$) czyli:

* zaburzeniu podlega 1 parameter (4 symulacje po jednej dla 'indywidualnie' zaburzonych parametrów $p_7\ldots p_{10}$),
*  zaburzeniu podlegaja 2 parametry (6 symulacji dla roznych par zaburzonych parametrów)
*  zaburzeniu podlegaja 3 parametry (4 symulacje dla jednego parametru niezaburzonego)
*  zaburzeniu podlegaja 4 parametry (1 symulacja dla wszystkich zaburzonych )

## Seria 1 - zaburzanie pojedynczego parametru

Zgodnie z wcześniejszym opisem pierwsza seria eksperymenów polega na pojedynczym zaburzaniu parametrow
$p_7\ldots p_{10}$ i weryfikacji możliwości odnalezienia przez hybrydowy solwer wartości referencyjnych tych parametrów
Eksperymenty przeprowadzano w ten sposób, że na poziomie alg ewolucyjnego zmieniano zakres dopuszczalnych wartości zaburzanego
parametru dla populacji początkowej , a następnie rozszerzano zakres dopuszczalny tego parametru w taki sposób aby obejmowal
on zakres referencyjny oraz ten wynikający z zaburzenia.  

Innymi słowy populacja początkowa zawierała osobniki z zaburzonymi wartościami wybranego parametru (wybranych parametrów)
natomiast wartości tego parametru dla osbników tworzonych w trakcie działania alg. ewolucyjnego
generowane były z szerszego zakresu tj. $zakres_{referencyjny} \uplus zakres_{zaburzony} \uplus zakres_{dodatkowy}$  

Wszystkie pozostałe parametry zarówno alg ewolucyjnego jak i wywołań symulatora wzrostu guza pozostawały niezmienione 
i były takie same jak opisane w sekcjach \ref{sec:algorytm} oraz \ref{sec:start} zwiekszając ilość ewaluacji ewolucji do 200.

Planowane do przyjęcia podczas eksperymentów zakresy zmienności poszczególnych analizowanych parametrów:

| Parametr | Zakres zmienności podczas wstępnej ewaluacji | Zakres zmienności dla populacji początkowej | Zakres zmienności w trakcie trwania ewolucji |
|----------|:-------------------:|:------------------:|:-------------------:|
| $p_7$ | $p_7 = 10 \pm 10\%$| $p_7 \in [11.0,15.0]$  | $p_7 \in [5.0,15.0]$  |   
|  $p_8$ | $p_8 = 2 \pm 10 \%$   | $p_{8} \in [2.1,3.0]$  |  $p_8 \in [1.0,3.0]$ |
|  $p_9$ | $p_9 = 10 \pm 10 \%$   |  $p_9 \in [11.0,15.0]$ |  $p_9 \in [5.0,15.0]$  |
|  $p_{10}$ | $p_{10} = 100 \pm 10 \%$   |  $p_{10} \in [110.0,150.0]$ |  $p_{10} \in [50.0,150.0]$ |


\pagebreak
\pagebreak

### Zaburzanie parametru   $p_7=o^{prol}$

####Parametry eksperymentu
Dla przypomnienia parametr $p_7=o^{prol}$ solwera mówi ile co najmniej tlenu potrzeba, żeby komórki się mnożyły.
Na wstępnym etapie jako wartość referencyjną tego parametru przyjęto jako  $p_7=o^{prol} \in [9.0,11.0]$ (z war ref. 10.0).

W pierwszym eksperymencie przyjęto zatem, iż początkowa seria wartości tego parametru (wartości tego parametru dla populacji
początkowej alg ewolucyjnego) losowana będzie z przedziału $p_7' \in [11.0,15.0]$
Natomiast dla osobników generowanych już w trakcie działania alg. ewolucyjnego jako dopuszczalny zakres wartości
parametru $p_7$ przyjęto zakres $p_7 \in [5.0,15.0]$ 

| Parametr | Zakres zmienności podczas wstępnej ewaluacji | Zakres zmienności dla populacji początkowej | Zakres zmienności w trakcie trwania ewolucji |
|----------|:-------------------:|:------------------:|:-------------------:|
| $p_7$ | $p_7 = 10 \pm 10\%$| $p_7 \in [11.0,15.0]$  | $p_7 \in [5.0,15.0]$  |  

#### Otrzymane wyniki
Ponizej odpowiednio: rozklad p7 w osobnikach na kolejnych etapach działania alg ewolucyjnego,
jego wartosci usrednione, maksymalne i minimalne w populacji, oraz odchylka sredniej wartosci $p_7$
w populacji od wartosci referencyjnej w kolejnych iteracjach.  

![Rozklad $p_7$ w populacji](figures/popp7.png){width=100%}

![Wartosci srednie, maksymalne oraz minimalne p7 na kolejnych etapach dzialania alg ewolucyjnego](figures/agrP7.png){width=100%}

![Odchylka sredniej wartosci p7 w populacji od wartosci referencyjnej](figures/Odchylkap7.png){width=100%}

\pagebreak

### Zaburzanie parametru   $p_8=o^{death}$

####Parametry eksperymentu
Dla przypomnienia parametr $p_8=o^{death}$ solwera mówi ile ile co najwyżej musi być tlenu, żeby  żyły.
Na wstępnym etapie jako wartość referencyjną tego parametru przyjęto jako  $p_8=o^{death} \in [1.8,2.2]$ (war ref. 2.0).

W niniejszym eksperymencie przyjęto zatem, iż początkowa seria wartości tego parametru (wartości tego parametru dla populacji
początkowej alg ewolucyjnego) losowana będzie z przedziału $p_7' \in [2.1,3.0]$
Natomiast dla osobników generowanych już w trakcie działania alg. ewolucyjnego jako dopuszczalny zakres wartości
parametru $p_8$ przyjęto zakres $p_8 \in [1.0,3.0]$ 

| Parametr | Zakres zmienności podczas wstępnej ewaluacji | Zakres zmienności dla populacji początkowej | Zakres zmienności w trakcie trwania ewolucji |
|----------|:-------------------:|:------------------:|:-------------------:|
|  $p_8$ | $p_8 = 2 \pm 10 \%$   | $p_{8} \in [2.1,3.0]$  |  $p_8 \in [1.0,3.0]$ |

#### Otrzymane wyniki

Ponizej odpowiednio: rozklad $p_8$ w osobnikach na kolejnych etapach działania alg ewolucyjnego,
jego wartosci usrednione, maksymalne i minimalne w populacji, oraz odchylka sredniej wartosci $p_7$
w populacji od wartosci referencyjnej w kolejnych iteracjach.  

![Rozklad $p_8$ w populacji](figures/popp8.png){width=100%}

![Wartosci srednie, maksymalne oraz minimalne p8 na kolejnych etapach dzialania alg ewolucyjnego](figures/agrP8.png){width=100%}

![Odchylka sredniej wartosci p8 w populacji od wartosci referencyjnej](figures/Odchylkap8.png){width=100%}

#### Napotkane problemy
Przy eksperymentowaniu z zaburzonym p8 nadziano sie na klopociki prawdopodobnie bedace pochodna/kontynuacja
problemów opisywanych wczesniej polegajacych na "niestabilnym" dzialaniu solwera przy niektorych wartosciach/kombinacjach parametrow

Ponizej wywolania solwera ktore sie nie powiodly (moze przyda sie kiedys do poprawek w solwerze)

Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.454590847505983 2.9222689984674073 9.063008773943382 96.1524747998914 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.430679318110801 2.9613064430274214 9.001731455509889 94.84556414555985 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.312816143866291 2.8373159607698737 9.068744087595107 109.4082352110131 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.45412400070996 2.9512270566632774 9.134113928012875 96.1524747998914 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.506700280337986 2.6729585594183844 9.127711756586537 98.28336497095415 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.428205108285592 2.6603929941110316 9.0677395656585 97.18615629884131 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.477124985184965 2.7878387123218205 9.071758596198237 108.77922017134355 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Read from err: terminate
Read from err: called
Read from err: after
Read from err: throwing
Read from err: an
Read from err: instance
Read from err: of
Read from err: 'std::bad_alloc'
Read from err: what():
Read from err: std::bad_alloc
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.45300605302644 2.991957323550271 9.06939941685454 96.1524747998914 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.424280747231732 2.816132673178266 9.080270122482826 109.95693142895279 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.454330657752786 2.6073509233841365 9.065938202205565 109.70008853564136 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.729087704398712 2.9222689984674073 9.146183592522865 98.30290819602372 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4




Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.312825087735012 2.9227547222493135 9.24885580468986 90.5577187348602 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.45412400070996 2.9512270566632774 9.11652843068546 96.1524747998914 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.454330657752786 2.7220574749335147 9.065931652601307 109.70008853564136 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.455134787600183 2.922261983693214 9.063918179902238 109.55449860456025 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.006641892977663 2.8373206073973387 9.002174195946964 93.65986847646002 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.454160715570499 2.9956881333657854 9.001542159250945 94.68929923984707 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.401906819035798 2.996356833670716 9.254686609769006 90.49447838526919 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.453536622315424 2.9424569354815837 9.249968498181236 96.1524747998914 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.454590847505983 2.9222689984674073 9.25062940767948 96.92921554534925 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.422952034739607 2.923597621538141 9.005725604299714 96.1524747998914 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4


\pagebreak
\pagebreak

### Zaburzanie parametru   $p_9=T^{prol}$ 

####Parametry eksperymentu
Dla przypomnienia parametr $p_9=T^{prol}$  solwera mówi jak wolno komórki się mnożą (większe - wolniej)
Na wstępnym etapie jako wartość referencyjną tego parametru przyjęto jako $p_9=T^{prol} \in [9.0,11.0]$ (war ref. 10.0)

W niniejszym eksperymencie przyjęto zatem, iż początkowa seria wartości tego parametru (wartości tego parametru dla populacji
początkowej alg ewolucyjnego) losowana będzie z przedziału $p_9' \in [11.0,15.0]$
Natomiast dla osobników generowanych już w trakcie działania alg. ewolucyjnego jako dopuszczalny zakres wartości
 parametru $p_9$ przyjęto zakres $p_9 \in [5.0,15.0]$ 

#### Otrzymane wyniki

Eksperyment w trakcie realizacji....


\pagebreak
\pagebreak

### Zaburzanie parametru   $p_{10}=T^{death}$

####Parametry eksperymentu
Dla przypomnienia parametr $p_{10}=T^{death}$ solwera mówi jak wolno umierają komórki.
Na wstępnym etapie jako wartość referencyjną tego parametru przyjęto jako $p_{10}=T^{death} \in [90.0,110.0]$ (war ref. 100.0)

W niniejszym eksperymencie przyjęto zatem, iż początkowa seria wartości tego parametru (wartości tego parametru dla populacji
początkowej alg ewolucyjnego) losowana będzie z przedziału $p_{10}' \in [110.0,150.0]$
Natomiast dla osobników generowanych już w trakcie działania alg. ewolucyjnego jako dopuszczalny zakres wartości
 parametru $p_{10}$ przyjęto zakres $p_{10} \in [50.0,150.0]$ 

#### Otrzymane wyniki

Eksperyment w trakcie realizacji .... 

\textbf{Eksperymentu ostatecznie nie wykonanu} 



## Kwestie do zweryfikowania 

* czy zaburzanie realizowane jak powyzej - ok?
* czy pprzyjmowana sila zaburzenia / zakres zmian parametru w pop poczatkowej ok/wystarczajacy?
* czy parametry ewolucji (ilosc ewaluacji / dlugosc trwania ok)?
* czy zakres gromadzonych / wizualizowanych wyników wystarczajacy? (moze potrzebne cos jeszcze? czas obliczen? masa guza? ... )


\newpage

#Eksperyment z fixowaniem wartosci parametrow niepodlegajacych ewolucji/poszukiwaniu 


##Sposób przeprowadzenia eksperymentow
W ponizszej serii eksperymentow podobnie jak wczesniej stopniowo zwiększamy 'stopień trudności' tzn zaburzeniu ulega stopniowo (w stosunku do zakresu referencyjnego) rosnąca ilość parametrów 
(tych przyjętych na wstępnym etapie weryfikacji jako istotne czyli odpowiednio:  $p_7=o^{prol}$, $p_8=o^{death}$,  $p_9=T^{prol}$ oraz $p_{10}=T^{death}$) czyli:

* zaburzeniu podlega 1 parameter (4 symulacje po jednej dla 'indywidualnie' zaburzonych parametrów $p_7\ldots p_{10}$),
*  zaburzeniu podlegaja 2 parametry (6 symulacji dla roznych par zaburzonych parametrów)
*  zaburzeniu podlegaja 3 parametry (4 symulacje dla jednego parametru niezaburzonego)
*  zaburzeniu podlegaja 4 parametry (1 symulacja dla wszystkich zaburzonych )

Z tym ze tym razem poza zaburzanymi i ewoluowanymi parametyrami wszystkie pozostale
byly "fixed". Czyli jesli zaburzamy pojedynyczy parametr $p_7$ to wszystkie pozostale ($p_8 \ldots p_{10}$ powinny zostac
"zafikswoane" na swoich wartosciach referencyjnych i nie byc poddawane ewolucji/poszukiwaniom.)

Podobnie jesli zaburzaniu ma podleagc para (np ${p_8}$ i $p_{10}$)   to pozostale dwa bedace przedmiotem zainteresowania 
powinny zostac zafiksowane na swoich wartosciach referencyjnych. 

##Problemy na jakie natrafiono
Niestety po wprowadzeniu do definicji solwera ewolucyjnego modyfikacji pozwalajacych prowadzic 
eksperymenty zgodnie z powyzszym opisem nadziano sie na problemy z dzialaniem solwera. 
Wydaje sie ze problemy mialy podobna nature jak poprzednio - przy jakiejs kombinacji parametrow 
nastepowal zbyt duzy rozrost (mapy) zyl co powodowalo, albo bardzo znaczne wydluzenie obliczen albo 
"wywrocenie" sie solwera z uwagi na brak mozliwosci dalszego alokowania pamieci etc. 

``
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.454590847505983 2.9222689984674073 9.063008773943382 96.1524747998914 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.430679318110801 2.9613064430274214 9.001731455509889 94.84556414555985 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.312816143866291 2.8373159607698737 9.068744087595107 109.4082352110131 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.45412400070996 2.9512270566632774 9.134113928012875 96.1524747998914 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.506700280337986 2.6729585594183844 9.127711756586537 98.28336497095415 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.428205108285592 2.6603929941110316 9.0677395656585 97.18615629884131 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.477124985184965 2.7878387123218205 9.071758596198237 108.77922017134355 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Read from err: terminate
Read from err: called
Read from err: after
Read from err: throwing
Read from err: an
Read from err: instance
Read from err: of
Read from err: 'std::bad_alloc'
Read from err: what():
Read from err: std::bad_alloc
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.45300605302644 2.991957323550271 9.06939941685454 96.1524747998914 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.424280747231732 2.816132673178266 9.080270122482826 109.95693142895279 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.454330657752786 2.6073509233841365 9.065938202205565 109.70008853564136 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.4
Unable to run solver: java.lang.NumberFormatException: For input string: "===================================================================================" mpirun -np 1 -host node13:1 ../TumorGenetyka/tumor 2 80 9900 0.1 1000 0.5 9.729087704398712 2.9222689984674073 9.146183592522865 98.30290819602372 0.001 0.3 0.625 0.3205 0.0064 0.0064 0.0000555 0.01 0.0000555 0.01 0.4 0.5 0.05 0.3 0.01333 10 0.003 2 5 25 24 0.003 0.
``

##Zmiany wprowadzone do solwera
Aby uniknac tego typu problemow, wprowadzono w solwerze nastepujaca zmiane

Dodano kod który uzależnia wzrost żył od aktualnego rozmiaru już istniejącego układu żył.
Model żył działa w ten sposób, że w określonych warunkach jest pewne prawdopodobieństwo 
że coś nowego się utworzy, zmiana polega na tym ze to prawdopodobieństwo jest mnożone 
przez liczbę inhibit_factor z przedziału [0, 1], która jest równa 1 dopóki 
ilość wierzchołków + żył jest mniejsza niż 10,000 (czyli wtedy prawdopodobieństwo 
utworzenia nowych jest takie samo jak przed tą zmianą), 
a jak jest ta ilość większa to odpowiednio inhibit_factor jest mniejszy,
dla układu z milionem+ staje się zerem. 
To powinno dość sztywno ograniczyć rozrost niezależnie od parametrów,
jednocześnie nie zmieniając wiele dla "rozsądnych" symulacji gdzie tych
żył się nie robią miliony.

##Aktualna postac funkcji celu    
Po powyzszych zmianach, poniewaz zmienia to model, przleiczono ponownie wartosci uzyskiwane przez 
solwer dla referencyjnych wartosci parametrow. 
Wartość ta aktualnie to 397321
 
A zatem funkcja fitnes to aktualnie: 
 
 \begin{equation}
abs(397321-tumor_{volume})
 \end{equation} 
 
 Wartość ta byla minimalizowana podczas ewolucji.
 
\newpage

##Zaburzanie 4 parametrów

###Zmiennosc parametrow

| Parametr | Zakres zmienności podczas wstępnej ewaluacji | Zakres zmienności dla populacji początkowej | Zakres zmienności w trakcie trwania ewolucji |
|----------|:-------------------:|:------------------:|:-------------------:|
| $p_7$ | $p_7 = 10 \pm 10\%$| $p_7 \in [11.0,15.0]$  | $p_7 \in [5.0,15.0]$ |   
|  $p_8$ | $p_8 = 2 \pm 10 \%$   | $p_{8} \in [2.1,3.0]$  |  $p_8 \in [1.0,3.0]$ |
|  $p_9$ | $p_9 = 10 \pm 10 \%$   |  $p_9 \in [11.0,15.0]$ |  $p_9 \in [5.0,15.0]$  |
|  $p_{10}$ | $p_{10} = 100 \pm 10 \%$   |  $p_{10} \in [110.0,150.0]$ |  $p_{10} \in [50.0,150.0]$ |



###Rozklad P7

![Rozklad $p_7$ w populacji przy zaburzaniu 4 parametrow](figures/popp7All4.png){width=75%}

![Wartosci srednie, maksymalne oraz minimalne p7 na kolejnych etapach dzialania alg ewolucyjnego przy zaburzaniu 4 parametrow](figures/agrP7All4.png){width=75%}

![Odchylka sredniej wartosci p7 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow](figures/Odchylkap7All4.png){width=90%}
 
![Procentowa Odchylka sredniej wartosci p7 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow](figures/ProcOdchylkap7All4.png){width=90%}

\newpage
  
###Rozklad P8

![Rozklad $p_8$ w populacji przy zaburzaniu 4 parametrow](figures/popp8All4.png){width=75%}

![Wartosci srednie, maksymalne oraz minimalne p8 na kolejnych etapach dzialania alg ewolucyjnego przy zaburzaniu 4 parametrow](figures/agrP8All4.png){width=75%}

![Odchylka sredniej wartosci p8 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow](figures/Odchylkap8All4.png){width=75%}

![Procentowa Odchylka sredniej wartosci p8 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow](figures/ProcOdchylkap8All4.png){width=75%}

\newpage

###Rozklad P9

![Rozklad $p_9$ w populacji przy zaburzaniu 4 parametrow](figures/popp9All4.png){width=75%}

![Wartosci srednie, maksymalne oraz minimalne p9 na kolejnych etapach dzialania alg ewolucyjnego przy zaburzaniu 4 parametrow](figures/agrP9All4.png){width=75%}

![Odchylka sredniej wartosci p9 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow](figures/Odchylkap9All4.png){width=75%}
 
![Procentowa Odchylka sredniej wartosci p9 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow](figures/ProcOdchylkap9All4.png){width=75%}

\newpage
 
###Rozklad P10
 
![Rozklad $p_{10}$ w populacji przy zaburzaniu 4 parametrow](figures/popp10All4.png){width=75%}

![Wartosci srednie, maksymalne oraz minimalne p10 na kolejnych etapach dzialania alg ewolucyjnego przy zaburzaniu 4 parametrow](figures/agrP10All4.png){width=75%}

![Odchylka sredniej wartosci p10 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow](figures/Odchylkap10All4.png){width=75%}
 
![Procentowa Odchylka sredniej wartosci p10 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow](figures/ProcOdchylkap10All4.png){width=75%}

 \newpage
 
###Rozklad TumorVolume
  
![Rozklad $TumorVolume$ w populacji przy zaburzaniu 4 parametrow](figures/popo1All4.png){width=75%}
 
![Wartosci srednie, maksymalne oraz minimalne TumorVolume na kolejnych etapach dzialania alg ewolucyjnego przy zaburzaniu 4 parametrow](figures/agro1All4.png){width=75%}
 
![Odchylka sredniej wartosci TumorVolume w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow](figures/Odchylkao1All4.png){width=75%}

![Procentowa Odchylka sredniej wartosci TumorVolume w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow](figures/ProcOdchylkao1All4.png){width=75%}

\newpage

###Wnioski
Ponizej odchylki wszyskiech 4 parametrow i tumorVolume od ich wartosci referencyjncych. 

![Procentowa Odchylka poszczegolnych parametrow i TumorVolume od wart referencyjnych](figures/ProcOdchylkaAll.png){width=75%}

![Zwiazek pomiedzy wartosciami parametrow $p_7$, $p_8$, $p_9$ i $p_{10}$ a objetoscia guza](figures/1.png){width=75%}


\newpage

Zbieznosc niby jest, ale nie wszedzie i niekoniecznie do wartosci referencyjnych

O ile odchylka rozmiaru guza od wartosci referencyjnej zmierza do 0 to juz od wartosciach
poszczegolnych parametrow powiedziec tego nie mozna. 

Co wiecej w przypadku p8 ta odchylka wrecz rosnie :) ale
- brak zbieznosci w przypadku p8 jest w zasadzie oczekiwany bo jak sie popatrzy na wykres 
zaleznosci tumorvolume z wartoscia tego parametru to nie widac tam trendu. Wraz ze zmianami p8 
wartosci tumorvolume owszem zmienia sie ale bez jakiegos specjalnego (a w zasadzie zadnego)
trendu - wiec i ewolucji trudno bylo podazac pewnie z p8 w jakims konkretnym kierunku.

- Troche zastanawiajace jest dlaczego efekt jest nieco inny w przypadku p10 skoro zaleznosc pomiedzy
tumorVolume a tym parametrem byla podobna do zaleznosci od p8

- to be continued..... 

\newpage


\newpage

##Zaburzanie 4 parametrów - wydluzone dzialanie 

###Zmiennosc parametrow

| Parametr | Zakres zmienności podczas wstępnej ewaluacji | Zakres zmienności dla populacji początkowej | Zakres zmienności w trakcie trwania ewolucji |
|----------|:-------------------:|:------------------:|:-------------------:|
| $p_7$ | $p_7 = 10 \pm 10\%$| $p_7 \in [11.0,15.0]$  | $p_7 \in [5.0,15.0]$ |   
|  $p_8$ | $p_8 = 2 \pm 10 \%$   | $p_{8} \in [2.1,3.0]$  |  $p_8 \in [1.0,3.0]$ |
|  $p_9$ | $p_9 = 10 \pm 10 \%$   |  $p_9 \in [11.0,15.0]$ |  $p_9 \in [5.0,15.0]$  |
|  $p_{10}$ | $p_{10} = 100 \pm 10 \%$   |  $p_{10} \in [110.0,150.0]$ |  $p_{10} \in [50.0,150.0]$ |

Zmiana w tym eksperymencie w stosunku do poprzednio opisywanego polegala 
na wydluzeniu dzialania alg ewolucyjnego do 600 ewaluacji (600 wywolan solwera)

###Rozklad P7

![Rozklad $p_7$ w populacji przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/popp7All4_600Ev.png){width=75%}

![Wartosci srednie, maksymalne oraz minimalne p7 na kolejnych etapach dzialania alg ewolucyjnego przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/agrP7All4_600Ev.png){width=75%}

![Odchylka sredniej wartosci p7 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/Odchylkap7All4_600Ev.png){width=90%}
 
![Procentowa Odchylka sredniej wartosci p7 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow](figures/ProcOdchylkap7All4_600Ev.png){width=90%}

\newpage
  
###Rozklad P8

![Rozklad $p_8$ w populacji przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/popp8All4_600Ev.png){width=75%}

![Wartosci srednie, maksymalne oraz minimalne p8 na kolejnych etapach dzialania alg ewolucyjnego przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/agrP8All4_600Ev.png){width=75%}

![Odchylka sredniej wartosci p8 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/Odchylkap8All4_600Ev.png){width=75%}

![Procentowa Odchylka sredniej wartosci p8 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/ProcOdchylkap8All4_600Ev.png){width=75%}

\newpage

###Rozklad P9

![Rozklad $p_9$ w populacji przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/popp9All4_600Ev.png){width=75%}

![Wartosci srednie, maksymalne oraz minimalne p9 na kolejnych etapach dzialania alg ewolucyjnego przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/agrP9All4_600Ev.png){width=75%}

![Odchylka sredniej wartosci p9 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/Odchylkap9All4_600Ev.png){width=75%}
 
![Procentowa Odchylka sredniej wartosci p9 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/ProcOdchylkap9All4_600Ev.png){width=75%}

\newpage
 
###Rozklad P10
 
![Rozklad $p_{10}$ w populacji przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/popp10All4_600Ev.png){width=75%}

![Wartosci srednie, maksymalne oraz minimalne p10 na kolejnych etapach dzialania alg ewolucyjnego przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/agrP10All4_600Ev.png){width=75%}

![Odchylka sredniej wartosci p10 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/Odchylkap10All4_600Ev.png){width=75%}
 
![Procentowa Odchylka sredniej wartosci p10 w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/ProcOdchylkap10All4_600Ev.png){width=75%}

 \newpage
 
###Rozklad TumorVolume
  
![Rozklad $TumorVolume$ w populacji przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/popo1All4_600Ev.png){width=75%}
 
![Wartosci srednie, maksymalne oraz minimalne TumorVolume na kolejnych etapach dzialania alg ewolucyjnego przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/agro1All4_600Ev.png){width=75%}
 
![Odchylka sredniej wartosci TumorVolume w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/Odchylkao1All4_600Ev.png){width=75%}

![Procentowa Odchylka sredniej wartosci TumorVolume w populacji od wartosci referencyjnej przy zaburzaniu 4 parametrow dla 600 ewaluacji](figures/ProcOdchylkao1All4_600Ev.png){width=75%}

\newpage

###Wnioski
Ponizej odchylki wszyskiech 4 parametrow i tumorVolume od ich wartosci referencyjncych. 

![Procentowa Odchylka poszczegolnych parametrow i TumorVolume od wart referencyjnych dla 600 ewaluacji](figures/ProcOdchylkaAll_600Ev.png){width=75%}

\newpage






##Zaburzanie 1 parametru

###Zaburzanie P7

####Zmiennosc parametru 

| Parametr | Zakres zmienności podczas wstępnej ewaluacji | Zakres zmienności dla populacji początkowej | Zakres zmienności w trakcie trwania ewolucji |
|----------|:-------------------:|:------------------:|:-------------------:|
| $p_7$ | $p_7 = 10 \pm 10\%$| $p_7 \in [11.0,15.0]$  | $p_7 \in [5.0,15.0]$  |   
|  $p_8$ | $p_8 = 2 \pm 10 \%$   | $p_8= 2$ |  $p_8 =2$ |
|  $p_9$ | $p_9 = 10 \pm 10 \%$   |  $p_9 =10$ |  $p_9 =10$  |
|  $p_{10}$ | $p_{10} = 100 \pm 10 \%$   |  $p_{10} =100$ |  $p_{10} =100$ |


####Rozklad p7 

![Rozklad $p_7$ w populacji przy zaburzaniu 1 parametru (P7)](figures/popp7All1.png){width=75%}

![Wartosci srednie, maksymalne oraz minimalne p7 na kolejnych etapach dzialania alg ewolucyjnego przy zaburzaniu p7](figures/agrP7All1.png){width=75%}

![Odchylka sredniej wartosci p7 w populacji od wartosci referencyjnej przy zaburzaniu p7](figures/Odchylkap7All1.png){width=90%}
 
![Procentowa Odchylka sredniej wartosci p7 w populacji od wartosci referencyjnej przy zaburzaniu p7](figures/ProcOdchylkap7All1.png){width=90%}

\newpage

####Rozklad TumorVolume
  
![Rozklad $TumorVolume$ w populacji przy zaburzaniu p7](figures/popo1All1.png){width=75%}
 
![Wartosci srednie, maksymalne oraz minimalne TumorVolume na kolejnych etapach dzialania alg ewolucyjnego przy zaburzaniu p7](figures/agro1All1.png){width=75%}
 
![Odchylka sredniej wartosci TumorVolume w populacji od wartosci referencyjnej przy zaburzaniu p7](figures/Odchylkao1All1.png){width=75%}

![Procentowa Odchylka sredniej wartosci TumorVolume w populacji od wartosci referencyjnej przy zaburzaniu p7](figures/ProcOdchylkao1All1.png){width=75%}

