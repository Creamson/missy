#$\alpha$-dominance
##Przegląd literatury
Po raz pierwszy opisana w artykule <http://ieeexplore-1ieee-1org-1000047mp037c.wbg2.bg.agh.edu.pl/document/934293/>  przez K.Ikeda et al. jako odpowiedź na problem występowania rozwiązań odpornych na dominację(DRSs - ang. Dominance Resistance Solutions). Zastosowanie $\alpha$-dominance, ma pomóc w rozwiązywaniu problemów, w których występują duże obszary rozwiązań odpornych na dominację w sensie pareto(przykład przedstawiony w kolejnym podrozdziale)

W artykule <http://ieeexplore-1ieee-1org-1000047mp03e0.wbg2.bg.agh.edu.pl/document/5364439/> przedstawiono opis ze strony statystycznej. Autorzy wskazują na zastosowanie $\alpha$-dominacji dla problemów zaszumionych. Ważną cechą $\alpha$-dominacji w kontekście statystycznym jest to, że nie zakłada ona rozkładu szumu.

W artykule <http://ieeexplore-1ieee-1org-1000047mp0410.wbg2.bg.agh.edu.pl/document/8288432/> przedstawiono opis działania wraz z porównaniem do klasycznej pareto-dominacji i zaproponowano ulepszenie $\alpha$-dominacji.

##Opis

$\alpha$-dominacja wprowadzona po raz pierwszy w celu wyeliminowania problemu rozwiązań odpornych na dominancję.
Rozwiązania odporne na dominację są znacznie gorsze od innych rozwiązań pod względem co najmniej jednego kryterium i przez to odległe od zbioru pareto, natomiast rozwiązania, które je dominują są bardzo trudno znajdowalne. 

Przykładem problemu w którym występują rozwiązania odporne na dominację, jest problem minimalizacji przedstawiony w artykule Ikedy et al. Minimalizowana jest funkcja dwóch zmiennych z trzema funkcjami celu.
\begin{displaymath}
\begin{aligned}
	&\underset{x,y}{\operatorname{min}} f_1, f_2, f_3 \\
	&f_1(x,y) = x^2 \\
	&f_2(x,y) = (x-20)^2 \\
	&f_3(x,y) = y^2 \\
	&-50\leq x \leq 50, \quad -50 \leq y \leq 50 \\
\end{aligned}
\end{displaymath}

Łatwo można wykazać, że zbiorem pareto jest odcinek:
\begin{displaymath}
	(x,y) = (x,0) , \quad 0 \leq x \leq 20
\end{displaymath}

Rozważając dwa punkty A=(8,40) i B=(12,16) obliczmy dla nich funkcje celu: 
\begin{displaymath}
\begin{aligned}
	&(f_1(A),f_2(A),f_3(A))=(64,144,1600) \\
	&(f_1(B),f_2(B),f_3(B))=(144,64,36) \\
\end{aligned}
\end{displaymath}

Widać, że punkt B jest znacznie lepszy ze względu na kryterium trzecie, suma wartości funkcji 1 i 2 jest identyczna dla obu punktów(wynika to z postaci tych funkcji). Punkt B natomiast nigdy nie zdominuje punktu A, ponieważ jest gorszy ze względu na kryterium pierwsze. Dla tak postawionego problemu punkt A może być zdominowany jedynie przez punkt leżący na prostej równoległej do osi y poniżej tego punktu. Przy numerycznym poszukiwaniu rozwiązania wśród liczb rzeczywistych prawodpodobieństwo trafienia na tę linię jest równe 0. Właściwość tą będą wykazywać wszystkie punkty leżące w obszarze: 
\begin{displaymath}
	0 < x <20, \quad -50 \leq y \leq 50.
\end{displaymath}

Powyższe rozważania przedstawiono na rysunku \ref{fig:drss}.

![Rozwiązania i obszary dominacji\label{fig:drss}](figures/drss.png){width=50%}

W celu wyeliminowania tego zjawiska wprowadzono $\alpha$-dominację w miejsce standardowej dominacji w sensie pareto. 
$\alpha$-dominacja jest relaksacją dominacji w sensie pareto, poprzez wprowadzenie "parametru wymiany". Jest to próba porównania czy też wyceny jednego kryterium za pomocą drugiego, jednak nie wprost a za pomocą górnych/dolnych ograniczeń. Poprzez analogię do kursów walut(przedstawioną w artykule Ikedy): trudno jest określić precyzyjnie kurs wymiany euro na dolara i odwrotnie, natomiast bezpiecznie można powiedzieć że 1 euro jest warte co najmniej 0.1 dolara i jeden dolar jest wart co najmniej 0.1 euro.
Dla każdej pary funkcji celu $f_i$, $f_j, i \neq j$ określamy parametr $\alpha_{ij}$, który określa dolne/górne ograniczenie wymiany i-tego kryterium na j-te:
\begin{displaymath}
	\alpha_{ij} \leq \frac{\Delta f_j}{\Delta f_i} \leq \frac{1}{\alpha_{ji}},
\end{displaymath}

gdzie $\Delta f_i$ oraz $\Delta f_j$ są równowartością zmian $f_i$ oraz $f_j$.

Uwzględniając parametr $\alpha_{ij}$ w celu rozszerzenia obszaru dominacji, nowe funkcje celu określone są wzorem:
\begin{displaymath}
	g_i(x) = f_i(x) + \sum_{j \neq i}^{m} \alpha_{ij}f_j(x), \quad (i=1,2,\ldots,M)
\end{displaymath}

Definicję $\alpha$-dominacji uzyskujemy wstawiając nowe funkcje celu do definicji dominacji w sensie pareto. Zatem:


\begin{definition}[Relacja $\alpha$-dominacji]
Rozwiązanie $\vec{x}^a$ $\alpha$-dominuje rozwiązanie $\vec{x}^b$
(rozwiązanie $\vec{x}^a$ jest rozwiązaniem lepszym od rozwiązania
$\vec{x}^b$)
($\vec{x}^a~\overset{\alpha}{\succ} \vec{x}^b$) dla problemu polioprymalizacji o funkcjach celi $f_1 \ldots f_M$ wtedy i~tylko wtedy, gdy:

\begin{displaymath}
  \vec{x}^a~\overset{\alpha}{\succ} \vec{x}^b\Leftrightarrow \left\{ \begin{array}{ll}
      g_j(\vec{x}^a)\ntriangleleft g_j(\vec{x}^b)~~~dla~~~j=1,2\ldots ,M\\
      \exists i\in \{1,2,\ldots ,M\}:~~g_i(\vec{x}^a)\vartriangleright g_i(\vec{x}^b)\\
          \end{array} \right.
\end{displaymath},
gdzie $g_i$ jest dane wzorem:
\begin{displaymath}
    g_i(x) = f_i(x) + \sum_{j \neq i}^{m} \alpha_{ij}f_j(x), \quad (i=1,2,\ldots,M)
\end{displaymath}
\end{definition}


Dla parametru wymiany $\alpha_{ij}$ równego 0, $\alpha$-dominacja sprowadza się do zwykłej dominacji w sensie pareto. Rozszerzenie obszaru dominacji przedstawiono na rysunku \ref{fig:alpha-comp}, na którym rozważane jest rozwiązanie i obszar przez nie dominowany na wykresie, gdzie osiami współrzędnych są funkcje celu. 
Rozpiętość przestrzeni rozwiązań $\alpha$-dominowanych przez x zależy od współczynnika $\alpha$, im jest on większy tym "ramiona" przestrzeni widoczne na rysunku \ref{fig:alpha-comp} są bardziej odchylone od półprostych ograniczających obszar rozwiązań dominowanych w sensie pareto.
Wynika to bezpośrednio z relacji nierówności pomiędzy przyrostem funkcji a współczynnikiem $\alpha$. Dla podanego niżej dwuwymiarowego przykładu możemy zapisać:
\begin{displaymath}
    \alpha_{12} \leq \frac{\Delta f_2}{\Delta f_1} \leq \frac{1}{\alpha_{21}}
\end{displaymath}

Oznaczając przez $\gamma$ kąt odchylenia "ramienia" zbioru rozwiązań $\alpha$-dominowanych, od "ramienia" zbioru rozwiązań dominowanych w sensie pareto, można zauważyć że:
\begin{displaymath}
    \tan{\gamma} = \frac{\Delta f_1}{\Delta f_2} = \alpha_{21}
\end{displaymath}

Stąd wprost wynika, jak będzie się zmieniać "rozpiętość" zbioru w zależności od $\alpha$.
Analogiczne rozumowanie można przeprowadzić dla drugiego ramienia.


![Porównanie $\alpha$-dominance i dominacji w sensie pareto\label{fig:alpha-comp}](figures/alpha-comparision.png){width=75%}

##Implementacja
$\alpha$-dominance została zaimplementowana w postaci komparatora w klasie AlphaDominanceComparator. Klasa dziedziczy po EmasDominanceComparator. 

* Parametry konstruktora klasy:
	* alphaValues
	Jest to macierz wartości współczynników $\alpha$. Element w wierszu i-tym i kolumnie j-tej odwzorowuje wartość $\alpha_{ij}$. Wartości na przekątnej są ignorowane.
* Użycie:
	
	Klasa implementuje interfejs Comparator. Jako argumenty metody compare podaje się  dwa obiekty typu JMetal5Agent. Wartością zwracaną jest liczba całkowita ze znakiem określająca dominację jednego agenta nad drugim.


##Eksperymenty
Dla każdego problemu ZDT1..6 został dziesięciokrotnie przeprowadzony eksperyment z wykorzystaniem komparatora używającego klasycznej dominacji, Area of Control Dominance oraz $\alpha$-dominacji z zestawami parametrów $\alpha_{ij}$ w zakresie 0.1 do 0.8 z krokiem 0.1. Przyjęto granice od 0.1 do 0.7, ponieważ przy dalszym zmniejszaniu wartości poniżej 0.1 zbliżamy działanie do klasycznej dominacji, natomiast powyzej 0.7 nie ma sensu szukać, bo już dla wartości bliskich 0.7 wartości wskaźników jakości znacznie spadały.

Dla algorytmu EMASa użyto parametrów:

* crossoverOperator
	* crossoverProbability: 1.0	
	* distributionIndex: 5
* mutationOperator
	* mutationProbability: 1.0 / ilość zmiennych
	* distributionIndex: 10.0
* strongDistributionOperator
	* mutationProbability: 1.0
	* distributionIndex: 20.0
* maxNumberOfIterations: 1000
* numberOfIslands: 1
* envEnergy: 170
* initialAgentResourceLevel: 1.0
* transferAgentResourceLevel: 1.0
* replaceOnlyIfBetter: 0
* comparator: jak wyżej

Eksperyment został przeprowadzony w celu znalezienia wartości parametrów $\alpha$, dla których zostaną uzyskane najlepsze wyniki. 
Poniżej zebrano najlepsze wartości parametrów $\alpha$ ze względu na test Friedmana ze  wskaźnikiem HV, dla kolejnych problemów:


* ZDT1:  $\alpha_{12}= 0.1$, $\alpha_{21} = 0.4$ 
* ZDT2:  $\alpha_{12}= 0.2$, $\alpha_{21} = 0.7$
* ZDT3:  $\alpha_{12}= 0.2$, $\alpha_{21} = 0.2$
* ZDT4:  $\alpha_{12}= 0.6$, $\alpha_{21} = 0.6$ 
* ZDT6:  $\alpha_{12}= 0.6$, $\alpha_{21} = 0.5$

Porównanie wyników w postaci świec japońskich i testów wilcoxona dla każdego problemu przedstawiono poniżej. Oprócz tego zamieszczono również dla każdego problemu wykres metryki HV od wartości parametrów($\alpha_{12}$, $\alpha_{21}$).

\newpage

![Wyniki testu Wilcoxona w metryce EP dla ZDT1\label{fig:alpha-wilcoxon-ep_ZDT1}](figures/alpha-wilcoxon-ep_ZDT1.png){width=100%}

![Ranking w postaci świec japońskich w metryce EP dla ZDT1\label{fig:alpha-boxplots-ep-ZDT1}](figures/alpha-boxplots-ep-ZDT1.png){width=100%}

![Wyniki testu Wilcoxona w metryce GD dla ZDT1\label{fig:alpha-wilcoxon-gd_ZDT1}](figures/alpha-wilcoxon-gd_ZDT1.png){width=100%}

![Ranking w postaci świec japońskich w metryce GD dla ZDT1\label{fig:alpha-boxplots-gd-ZDT1}](figures/alpha-boxplots-gd-ZDT1.png){width=100%}

![Wyniki testu Wilcoxona w metryce HV dla ZDT1\label{fig:alpha-wilcoxon-hv_ZDT1}](figures/alpha-wilcoxon-hv_ZDT1.png){width=100%}

![Ranking w postaci świec japońskich w metryce HV dla ZDT1\label{fig:alpha-boxplots-hv-ZDT1}](figures/alpha-boxplots-hv-ZDT1.png){width=100%}

![Wyniki testu Wilcoxona w metryce IGD dla ZDT1\label{fig:alpha-wilcoxon-igd_ZDT1}](figures/alpha-wilcoxon-igd_ZDT1.png){width=100%}

![Ranking w postaci świec japońskich w metryce IGD dla ZDT1\label{fig:alpha-boxplots-igd-ZDT1}](figures/alpha-boxplots-igd-ZDT1.png){width=100%}

![Wyniki testu Wilcoxona w metryce IGD+ dla ZDT1\label{fig:alpha-wilcoxon-igd+_ZDT1}](figures/alpha-wilcoxon-igd+_ZDT1.png){width=100%}

![Ranking w postaci świec japońskich w metryce IGD+ dla ZDT1\label{fig:alpha-boxplots-igd+-ZDT1}](figures/alpha-boxplots-igd+-ZDT1.png){width=100%}

![Wyniki testu Wilcoxona w metryce SPREAD dla ZDT1\label{fig:alpha-wilcoxon-spread_ZDT1}](figures/alpha-wilcoxon-spread_ZDT1.png){width=100%}

![Ranking w postaci świec japońskich w metryce SPREAD dla ZDT1\label{fig:alpha-boxplots-spread-ZDT1}](figures/alpha-boxplots-spread-ZDT1.png){width=100%}

![Wykres metryki HV w zależnośći od parametrów alfa dla ZDT1\label{fig:alpha-plot-ZDT1}](figures/alpha-plot-ZDT1.png){width=100%}

\newpage


![Wyniki testu Wilcoxona w metryce EP dla ZDT2\label{fig:alpha-wilcoxon-ep_ZDT2}](figures/alpha-wilcoxon-ep_ZDT2.png){width=100%}

![Ranking w postaci świec japońskich w metryce EP dla ZDT2\label{fig:alpha-boxplots-ep-ZDT2}](figures/alpha-boxplots-ep-ZDT2.png){width=100%}

![Wyniki testu Wilcoxona w metryce GD dla ZDT2\label{fig:alpha-wilcoxon-gd_ZDT2}](figures/alpha-wilcoxon-gd_ZDT2.png){width=100%}

![Ranking w postaci świec japońskich w metryce GD dla ZDT2\label{fig:alpha-boxplots-gd-ZDT2}](figures/alpha-boxplots-gd-ZDT2.png){width=100%}

![Wyniki testu Wilcoxona w metryce HV dla ZDT2\label{fig:alpha-wilcoxon-hv_ZDT2}](figures/alpha-wilcoxon-hv_ZDT2.png){width=100%}

![Ranking w postaci świec japońskich w metryce HV dla ZDT2\label{fig:alpha-boxplots-hv-ZDT2}](figures/alpha-boxplots-hv-ZDT2.png){width=100%}

![Wyniki testu Wilcoxona w metryce IGD dla ZDT2\label{fig:alpha-wilcoxon-igd_ZDT2}](figures/alpha-wilcoxon-igd_ZDT2.png){width=100%}

![Ranking w postaci świec japońskich w metryce IGD dla ZDT2\label{fig:alpha-boxplots-igd-ZDT2}](figures/alpha-boxplots-igd-ZDT2.png){width=100%}

![Wyniki testu Wilcoxona w metryce IGD+ dla ZDT2\label{fig:alpha-wilcoxon-igd+_ZDT2}](figures/alpha-wilcoxon-igd+_ZDT2.png){width=100%}

![Ranking w postaci świec japońskich w metryce IGD+ dla ZDT2\label{fig:alpha-boxplots-igd+-ZDT2}](figures/alpha-boxplots-igd+-ZDT2.png){width=100%}

![Wyniki testu Wilcoxona w metryce SPREAD dla ZDT2\label{fig:alpha-wilcoxon-spread_ZDT2}](figures/alpha-wilcoxon-spread_ZDT2.png){width=100%}

![Ranking w postaci świec japońskich w metryce SPREAD dla ZDT2\label{fig:alpha-boxplots-spread-ZDT2}](figures/alpha-boxplots-spread-ZDT2.png){width=100%}

![Wykres metryki HV w zależnośći od parametrów alfa dla ZDT2\label{fig:alpha-plot-ZDT2}](figures/alpha-plot-ZDT2.png){width=100%}

\newpage


![Wyniki testu Wilcoxona w metryce EP dla ZDT3\label{fig:alpha-wilcoxon-ep_ZDT3}](figures/alpha-wilcoxon-ep_ZDT3.png){width=100%}

![Ranking w postaci świec japońskich w metryce EP dla ZDT3\label{fig:alpha-boxplots-ep-ZDT3}](figures/alpha-boxplots-ep-ZDT3.png){width=100%}

![Wyniki testu Wilcoxona w metryce GD dla ZDT3\label{fig:alpha-wilcoxon-gd_ZDT3}](figures/alpha-wilcoxon-gd_ZDT3.png){width=100%}

![Ranking w postaci świec japońskich w metryce GD dla ZDT3\label{fig:alpha-boxplots-gd-ZDT3}](figures/alpha-boxplots-gd-ZDT3.png){width=100%}

![Wyniki testu Wilcoxona w metryce HV dla ZDT3\label{fig:alpha-wilcoxon-hv_ZDT3}](figures/alpha-wilcoxon-hv_ZDT3.png){width=100%}

![Ranking w postaci świec japońskich w metryce HV dla ZDT3\label{fig:alpha-boxplots-hv-ZDT3}](figures/alpha-boxplots-hv-ZDT3.png){width=100%}

![Wyniki testu Wilcoxona w metryce IGD dla ZDT3\label{fig:alpha-wilcoxon-igd_ZDT3}](figures/alpha-wilcoxon-igd_ZDT3.png){width=100%}

![Ranking w postaci świec japońskich w metryce IGD dla ZDT3\label{fig:alpha-boxplots-igd-ZDT3}](figures/alpha-boxplots-igd-ZDT3.png){width=100%}

![Wyniki testu Wilcoxona w metryce IGD+ dla ZDT3\label{fig:alpha-wilcoxon-igd+_ZDT3}](figures/alpha-wilcoxon-igd+_ZDT3.png){width=100%}

![Ranking w postaci świec japońskich w metryce IGD+ dla ZDT3\label{fig:alpha-boxplots-igd+-ZDT3}](figures/alpha-boxplots-igd+-ZDT3.png){width=100%}

![Wyniki testu Wilcoxona w metryce SPREAD dla ZDT3\label{fig:alpha-wilcoxon-spread_ZDT3}](figures/alpha-wilcoxon-spread_ZDT3.png){width=100%}

![Ranking w postaci świec japońskich w metryce SPREAD dla ZDT3\label{fig:alpha-boxplots-spread-ZDT3}](figures/alpha-boxplots-spread-ZDT3.png){width=100%}

![Wykres metryki HV w zależnośći od parametrów alfa dla ZDT3\label{fig:alpha-plot-ZDT3}](figures/alpha-plot-ZDT3.png){width=100%}

\newpage


![Wyniki testu Wilcoxona w metryce EP dla ZDT4\label{fig:alpha-wilcoxon-ep_ZDT4}](figures/alpha-wilcoxon-ep_ZDT4.png){width=100%}

![Ranking w postaci świec japońskich w metryce EP dla ZDT4\label{fig:alpha-boxplots-ep-ZDT4}](figures/alpha-boxplots-ep-ZDT4.png){width=100%}

![Wyniki testu Wilcoxona w metryce GD dla ZDT4\label{fig:alpha-wilcoxon-gd_ZDT4}](figures/alpha-wilcoxon-gd_ZDT4.png){width=100%}

![Ranking w postaci świec japońskich w metryce GD dla ZDT4\label{fig:alpha-boxplots-gd-ZDT4}](figures/alpha-boxplots-gd-ZDT4.png){width=100%}

![Wyniki testu Wilcoxona w metryce HV dla ZDT4\label{fig:alpha-wilcoxon-hv_ZDT4}](figures/alpha-wilcoxon-hv_ZDT4.png){width=100%}

![Ranking w postaci świec japońskich w metryce HV dla ZDT4\label{fig:alpha-boxplots-hv-ZDT4}](figures/alpha-boxplots-hv-ZDT4.png){width=100%}

![Wyniki testu Wilcoxona w metryce IGD dla ZDT4\label{fig:alpha-wilcoxon-igd_ZDT4}](figures/alpha-wilcoxon-igd_ZDT4.png){width=100%}

![Ranking w postaci świec japońskich w metryce IGD dla ZDT4\label{fig:alpha-boxplots-igd-ZDT4}](figures/alpha-boxplots-igd-ZDT4.png){width=100%}

![Wyniki testu Wilcoxona w metryce IGD+ dla ZDT4\label{fig:alpha-wilcoxon-igd+_ZDT4}](figures/alpha-wilcoxon-igd+_ZDT4.png){width=100%}

![Ranking w postaci świec japońskich w metryce IGD+ dla ZDT4\label{fig:alpha-boxplots-igd+-ZDT4}](figures/alpha-boxplots-igd+-ZDT4.png){width=100%}

![Wyniki testu Wilcoxona w metryce SPREAD dla ZDT4\label{fig:alpha-wilcoxon-spread_ZDT4}](figures/alpha-wilcoxon-spread_ZDT4.png){width=100%}

![Ranking w postaci świec japońskich w metryce SPREAD dla ZDT4\label{fig:alpha-boxplots-spread-ZDT4}](figures/alpha-boxplots-spread-ZDT4.png){width=100%}

![Wykres metryki HV w zależnośći od parametrów alfa dla ZDT4\label{fig:alpha-plot-ZDT4}](figures/alpha-plot-ZDT4.png){width=100%}

\newpage

![Wyniki testu Wilcoxona w metryce EP dla ZDT6\label{fig:alpha-wilcoxon-ep_ZDT6}](figures/alpha-wilcoxon-ep_ZDT6.png){width=100%}

![Ranking w postaci świec japońskich w metryce EP dla ZDT6\label{fig:alpha-boxplots-ep-ZDT6}](figures/alpha-boxplots-ep-ZDT6.png){width=100%}

![Wyniki testu Wilcoxona w metryce GD dla ZDT6\label{fig:alpha-wilcoxon-gd_ZDT6}](figures/alpha-wilcoxon-gd_ZDT6.png){width=100%}

![Ranking w postaci świec japońskich w metryce GD dla ZDT6\label{fig:alpha-boxplots-gd-ZDT6}](figures/alpha-boxplots-gd-ZDT6.png){width=100%}

![Wyniki testu Wilcoxona w metryce HV dla ZDT6\label{fig:alpha-wilcoxon-hv_ZDT6}](figures/alpha-wilcoxon-hv_ZDT6.png){width=100%}

![Ranking w postaci świec japońskich w metryce HV dla ZDT6\label{fig:alpha-boxplots-hv-ZDT6}](figures/alpha-boxplots-hv-ZDT6.png){width=100%}

![Wyniki testu Wilcoxona w metryce IGD dla ZDT6\label{fig:alpha-wilcoxon-igd_ZDT6}](figures/alpha-wilcoxon-igd_ZDT6.png){width=100%}

![Ranking w postaci świec japońskich w metryce IGD dla ZDT6\label{fig:alpha-boxplots-igd-ZDT6}](figures/alpha-boxplots-igd-ZDT6.png){width=100%}

![Wyniki testu Wilcoxona w metryce IGD+ dla ZDT6\label{fig:alpha-wilcoxon-igd+_ZDT6}](figures/alpha-wilcoxon-igd+_ZDT6.png){width=100%}

![Ranking w postaci świec japońskich w metryce IGD+ dla ZDT6\label{fig:alpha-boxplots-igd+-ZDT6}](figures/alpha-boxplots-igd+-ZDT6.png){width=100%}

![Wyniki testu Wilcoxona w metryce SPREAD dla ZDT6\label{fig:alpha-wilcoxon-spread_ZDT6}](figures/alpha-wilcoxon-spread_ZDT6.png){width=100%}

![Ranking w postaci świec japońskich w metryce SPREAD dla ZDT6\label{fig:alpha-boxplots-spread-ZDT6}](figures/alpha-boxplots-spread-ZDT6.png){width=100%}

![Wykres metryki HV w zależnośći od parametrów alfa dla ZDT6\label{fig:alpha-plot-ZDT6}](figures/alpha-plot-ZDT6.png){width=100%}


##Porównanie działania z innymi komparatorami

Poniżej zebrano średnie(z wszystkich 10 prób) wyniki testów statystycznych dla komparatorów używających: standardowej dominacji, Area of Control Dominance oraz wizualizację rozwiązań i czasowych przebiegów HVR i IGD+ dla problemów ZDT1..ZDT6.
Dla każdego problemu przedstawiono również wyniki testów Wilcoxona oraz rankingi w postaci świec japońskich.

\newpage
###ZDT1 $\alpha_{12} = 0.1 \alpha_{21} = 0.4$

|        | Pareto Dominacja | Area of Control Dominance  | $\alpha$-dominance |
| ------ |:----------------:| :-------------------------:| :----------------: |
| EP     |        4.18e-01  |         	3.82e-02	 |         1.06e-01   |
| SPREAD |        9.73e-01  |      	6.39e-01	 |         9.36e-01   |
| GD     |        5.67e-04  |      	4.26e-03	 |         3.37e-04   |
| HV   	 |        5.03e-01  |      	6.24e-01	 |         6.29e-01   |
| IGD    |        3.97e-03  |       	4.88e-04	 |         1.25e-03   |
| IGD+   |        8.23e-02  |      	2.76e-02	 |         2.09e-02   | 

![Porównanie działania dominacji dla problemu ZDT1\label{fig:alpha-ZDT1}](figures/alpha_ZDT1.png){width=100%}

![Wyniki testu Wilcoxona dla ZDT1\label{fig:wilcoxon_ZDT1}](figures/tables_ZDT1.png){width=100%}

![Ranking w postaci świec japońskich dla ZDT1\label{fig:boxplots-ZDT1}](figures/boxplots_ZDT1.png){width=100%}


Według testu HV najlepszy jest komparator używający Area of Control Dominance, natomiast $\alpha$-dominacja jest od niego nieznacznie gorsza. Dominacja w sensie pareto znacznie odstaje od reszty. $\alpha$-dominacja zyskuje przewagę nad standardową dominacją poprzez rozmieszczenie rozwiązań szerzej na froncie, ponieważ rozwiązania, przesuniete na prawo mogą zdominować te leżące nieznacznie na lewo od nich. Area of Control Dominance rozmieszcza rozwiązania jeszcze szerzej, na całej szerokości frontu, natomiast rozwiązania są od niego nieco odsunięte, stąd metryka HV pokazuje jedynie niewielką przewagę nad $\alpha$-dominacją.

\newpage
###ZDT2 $\alpha_{12} = 0.2 \alpha_{21} = 0.7$

|        | Pareto Dominacja | Area of Control Dominance  | $\alpha$-dominance |
| ------ |:----------------:| :-------------------------:| :----------------: |
| EP     |        8.24e-01  |         	3.02e-01	 |         7.17e-01   |
| SPREAD |        1.05      |      	7.94e-01	 |         1.13       |
| GD     |        9.55e-05  |      	5.42e-04	 |         3.74e-04   |
| HV   	 |        1.23e-01  |      	2.69e-01	 |         1.72e-01   |
| IGD    |        6.13e-04  |	        2.32e-04	 |         6.14e-04   |
| IGD+   |        1.01e-02  |      	8.76e-03	 |         1.22e-02   | 

![Porównanie działania dominacji dla problemu ZDT2\label{fig:alpha-ZDT2}](figures/alpha_ZDT2.png){width=100%}

![Wyniki testu Wilcoxona dla ZDT2\label{fig:wilcoxon_ZDT2}](figures/tables_ZDT2.png){width=100%}

![Ranking w postaci świec japońskich dla ZDT2\label{fig:boxplots-ZDT2}](figures/boxplots_ZDT2.png){width=100%}

W tym przypadku również najlepiej radzi sobie Area of Control Dominance, który przewagę zyskuje rozkładając rozwiązania na całej szerokości frontu, podczas gdy $\alpha$-dominacja i dominacja w sensie pareto ma tendencję do ściągania rozwiązania do lewewego naroża frontu.

\newpage
###ZDT3 $\alpha_{12} = 0.2 \alpha_{21} = 0.2$

|        | Pareto Dominacja | Area of Control Dominance  | $\alpha$-dominance |
| ------ |:----------------:| :-------------------------:| :----------------: |
| EP     |        0.573     |         	0.0462		 |         0.146      |
| SPREAD |        1.05      |      	0.898		 |         1.48       |
| GD     |        5.09e-04  |      	2.31e-03	 |         1.09e-04   |
| HV   	 |        3.52e-01  |      	4.79e-01	 |         5.00e-01   |
| IGD    |        1.01e-02  |       	4.72e-04	 |         5.87e-03   |
| IGD+   |        3.57e-01  |      	3.02e-02	 |         1.96e-01   | 

![Porównanie działania dominacji dla problemu ZDT3\label{fig:alpha-ZDT3}](figures/alpha_ZDT3.png){width=100%}

![Wyniki testu Wilcoxona dla ZDT3\label{fig:wilcoxon_ZDT3}](figures/tables_ZDT3.png){width=100%}

![Ranking w postaci świec japońskich dla ZDT3\label{fig:boxplots-ZDT3}](figures/boxplots_ZDT3.png){width=100%}

 Według metryki HV $\alpha$-dominacja daje najlepsze rezultaty, natomiast widać, że rozwiązania uzyskane za jej pomocą nie pokrywają wszystkich kawałków frontu, jak w przypadku Area of Control Dominance. Rozwiązania uzyskane za pomocą $\alpha$-dominacji są za to bardziej zbliżone do frontu, niemal się z nim nakładają.

\newpage
###ZDT4 $\alpha_{12} = 0.6 \alpha_{21} = 0.6$

|        | Pareto Dominacja | Area of Control Dominance  | $\alpha$-dominance |
| ------ |:----------------:| :-------------------------:| :----------------: |
| EP     |        4.33e-01  |         	4.34e-01	 |         1.75e-01   |
| SPREAD |        1.06      |      	1.00		 |         9.39e-01   |
| GD     |        3.20e-03  |      	3.71e-03	 |         1.08e-02   |
| HV   	 |        4.73e-01  |      	4.65e-01	 |         5.53e-01   |
| IGD    |        5.56e-03  |       	5.55e-03	 |         3.24e-03   |
| IGD+   |        1.16e-01  |      	1.28e-01	 |         8.04e-02   | 


![Porównanie działania dominacji dla problemu ZDT4\label{fig:alpha-ZDT4}](figures/alpha_ZDT4.png){width=100%}

![Wyniki testu Wilcoxona dla ZDT4\label{fig:wilcoxon_ZDT4}](figures/tables_ZDT4.png){width=100%}

![Ranking w postaci świec japońskich dla ZDT4\label{fig:boxplots-ZDT4}](figures/boxplots_ZDT4.png){width=100%}

Dla tego problemu $\alpha$-dominacja jest wyraźnie najlepsza, spośród testowanych dominacji. Różnice między nimi w testach są znaczne. Wynika to z tego, że początkowe rozwiązania mają bardzo duże wartości drugiej funkcji celu(oś y). Dlatego w przypadku Area of Control Dominance i klasycznej dominacji rozwiązania bardzo szybko zbiegają do osi y i w momencie zejścia "niżej" nie mają już szans się rozproszyć, ponieważ nie ma agentów w prawej części, którzy mogliby je zdominować. Duże wartości wspólczynników $\alpha$ natomiast umożliwiają wybieranie rozwiązań gorszych pod względem drugiej funkcji celu(odsunięte od osi y), co pozwala im przetrwać do momentu zejścia rozwiązań w kierunku osi x. Ze względu na szybszą zbieżność dla $\alpha$-dominacji, te rozwiązania oddalone od osi y mają szansę przetrwać, poniważ ich odległość od osi x zostanie szybko zmniejszona. 

\newpage
###ZDT6 $\alpha_{12} = 0.6 \alpha_{21} = 0.5$

|        | Pareto Dominacja | Area of Control Dominance  | $\alpha$-dominance |
| ------ |:----------------:| :------------------------: | :----------------: |
| EP     |        7.57e-02  |         	1.21e-01	 |         5.6e-03    |
| SPREAD |        5.92e-01  |      	5.22e-01	 |         6.96e-01   |
| GD     |        3.72e-03  |      	5.22e-03	 |         1.06e-03   |
| HV   	 |        3.55e-01  |      	3.29e-01	 |         3.92e-01   |
| IGD    |        1.14e-03  |       	1.74e-03	 |         4.74e-04   |
| IGD+   |        3.92e-02  |      	6.04e-02	 |         1.47e-02   | 


![Porównanie działania dominacji dla problemu ZDT6\label{fig:alpha-ZDT6}](figures/alpha_ZDT6.png){width=100%}

![Wyniki testu Wilcoxona dla ZDT6\label{fig:wilcoxon_ZDT6}](figures/tables_ZDT6.png){width=100%}

![Ranking w postaci świec japońskich dla ZDT6\label{fig:boxplots-ZDT6}](figures/boxplots_ZDT6.png){width=100%}

W tym przypadku również $\alpha$-dominacja jest najlepsza pod względem testów HV, natomiast, dominacja w sensie pareto jest od niej niewiele gorsza, Area of Control dominance wypada najgorzej.
Podobnie jak dla problemu ZDT4 początkowe rozwiązania rozlokowane są dość "wysoko", więc uzasadnienie jest tutaj podobne. 


###Podsumowanie
Największą zaletą rozwiązania wszystkich problemów z użyciem $\alpha$-dominacji jest znacznie szybsza zbieżność, w porównaniu z pozostałymi dominacjami. W każdym przypadku rozwiązania znajdowane są bardzo szybko w początkowych iteracjach.

Wadą natomiast jest tendencja do "ściągania" rozwiązania do lewego naroża frontów, widoczne dla problemu ZDT2, gdzie front jest wypukły. Analiza tego zjawiska przedstawiona jest na rysunkach \ref{fig:zdt2-analiza-pareto} - \ref{fig:zdt2-analiza-area}.

Na rysunku \ref{fig:zdt2-analiza-pareto}, przedstawiono działanie klasycznej dominacji. Agenci z lewej strony, mają znacznie większe szanse na zdominowanie innych agentów. Obszar dominacji agenta A2 jest niemal pusty.

Na rysunku \ref{fig:zdt2-analiza-alpha} przedstawiono działanie $\alpha$-dominacji. Widać, że problem, który występował w dominacji w sensie pareto jest tutaj jeszcze bardziej wyraźny. Agent A1 wcześniej nie dominował Agenta A2, natomiast teraz, agent A1 dominuje niemal całą przestrzeń rozwiązań co skutkuje znacznym skupianiem się rozwiązań w lewym narożu frontu.

Problem jest szczególnie widoczny w przypadku ZDT2, ponieważ front jest wypukły i rozwiązania które są dominowane leżą bliżej frontu, ich odległość jest mniejsza, zatem są bliżej rozwiązań optymalnych w sensie pareto niż w przypadku rozwiązań leżących w tym samym miejscu dla problemów z wklęsłym frontem.

Rozwiązaniem mogłoby być adaptacyjne zwiększanie współczynników $\alpha$ dla rozwiązań w zależności od wartości drugiej funkcji celu. 

Na rysunku \ref{fig:zdt2-analiza-area}, widać, że Area of Control Dominance jest odporna na to zjawisko. A1 i A2 są wzajemnie niezdominowane, zatem ich obszary dominacji sa łączone i obaj mają jednakowe szanse na zdominowanie agenta A4, podczas gdy w poprzednich przypadkach agent A4 zawsze był dominowany tylko przez agenta A1, a obszar dominacji agenta A2 był pusty.


![Dominacja pareto \label{fig:zdt2-analiza-pareto}](figures/zdt2_pareto.png){width=100%}

![Alfa dominacja \label{fig:zdt2-analiza-alpha}](figures/zdt2_alpha.png){width=100%}

![Area of Control Dominance\label{fig:zdt2-analiza-area}](figures/zdt2_area.png){width=100%}


