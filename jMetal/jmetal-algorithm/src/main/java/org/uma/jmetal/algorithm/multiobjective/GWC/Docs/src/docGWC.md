# Grey Wolf Optimizer

## Inspiracja
Algorytm GWO został zaproponowany w 2014 roku przez S. Mirjalili \cite{gwo}. Inspiracją do jego powstania była unikatowa struktura hierarchii wśród wilków oraz sposób w jaki polują. 
Najpotężniejszym wilkiem jest osobnik $\alpha$, który przewodzi całej grupie w polowaniu, migracji i karmieniu, Kiedy wilk $\alpha$ jest poza grupą, chory lub martwy, najsilniejszy wilk z grupy $\beta$ przejmuje przywództwo. Władza i dominacja wilków $\delta$ i $\omega$ są dużo mniejsze niż wilków $\alpha$ i $\beta$. Drugą inspiracją jest sposób w jaki wilki polują. Głównymi fazami polowania są:
\begin{itemize}
    \item Śledzenie, pogoń i zbliżenie się do ofiary.
    \item Pościg, otoczenie i napastowanie ofiary.
    \item Atak ofiary.
\end{itemize}

## Model matematyczny
### Hierarchia
Aby matematycznie zamodelować hierarchię wśród wilków, najlepiej dostosowane rozwiązanie uznajemy za $\alpha$. Konsekwentnie drugie i trzecie najlepiej dostosowane rozwiązania to $\beta$ i $\delta$. Pozostałe rozwiązania kandydujące określane są jako $\omega$. W GWO polowanie (optymalizacja) jest przewodzona przez $\alpha$, $\beta$ i $\delta$. Pozostałe wilki $\omega$ podążają za nimi.

### Otoczenie ofiary
Wilki w trakcie polowania okrążają ofiarę. Aby matematycznie zamodelować to zachowanie zaproponowano następujące równania:
\begin{equation}
    \vec{D} = |\vec{C} \cdot \vec{X}_p(t) - \vec{X}(t)| \label{eq:1}
\end{equation}
\begin{equation}
    \vec{X}(t+1) = \vec{X}_p(t) - \vec{A} \cdot \vec{D} \label{eq:2}
\end{equation}
gdzie $t$ oznacza aktualną iterację. $\vec{A}$ i$\vec{C}$ są wektorami współczynników. $\vec{X}_p$ jest wektorem pozycji ofiary, a $\vec{X}$ to wektor pozycji wilka. 
Wektory $\vec{A}$ i $\vec{C}$ są obliczane:
\begin{equation}
    \vec{A} = 2\vec{a} \cdot \vec{r}_1 - \vec{a}
\end{equation}
\begin{equation}
    \vec{C} = 2 \cdot \vec{r}_2 \label{eq:4}
\end{equation}
gdzie elementy $\vec{a}$ liniowo zmniejszają się z 2 do 0 w trakcie kolejnych iteracji, a $\vec{r}_1$ i $\vec{r}_2$ są losowymi wektorami z przedziału $[0, 1]$

Aby zobaczyć efekty równania \ref{eq:1} i \ref{eq:2} zilustrowano wektor dwuwymiarowy i kilku możliwych sąsiadów na obrazku 1(a). Tak jak można zauważyć, wilk na pozycji $(X, Y)$ może zaktualizować swoją pozcyję zgodnie z pozycją ofiary $(X^*, Y^*)$. Można osiągnąć różne miejsca wokoł najlepszego agenta w odniesieniu do aktualnejpozycji poprzez dostosowanie wartości wektorów $\vec{A}$ i $\vec{C}$. Na przykład, $(X^*-X, Y^*)$ moze być osiągnięte przez ustawienie $\vec{A} = (1, 0)$ i $\vec{C} = (1, 1)$. Możliwe aktualizacje pozycji agenta w przestrzeni 3D prezentuje obrazek 1(b). Należy zauważyć, że losowe wektoy $\vec{r}_1$ i $\vec{r}_2$ pozwalają wilkowi na osiągnięcie każdej pozycji pomiędzy punktami zilustrowanymi na obrazku 1. Czyli agent może zaktualizować swoją pozycję w przestrzeni dookoła ofiary w dowolnej losowej lokalizacji przy pomocy równań \ref{eq:1} i \ref{eq:2}.

Ta koncepcja może zostać rozszerzona na przestrzeń przeszukiwań o $n$ wymiarach. Agenci poruszają się w hiperkostkach wokół najlepszego rozwiązania uzyskanego do tej pory.

![Wektory pozycji 2D i 3D i ich możliwe kolejne lokacje. Źródło \cite{gwo}](figures/fig1.png){ width=50% }

### Polowanie
Wilki posiadają umiejętność lokalizacji ofiary i okrążenia jej. Polowanie jest zwykle prowadzone przez $\alpha$. $\beta$ i $\delta$ mogą również okazjonalnie uczestniczyć w polowaniu. Jednakże w abstrakcyjnej przestrzeni przeszukiwań nie mamy informacji o lokalizacji optimum (ofiary). Aby matematycznie zamodelować proces polowania wśród wilków zakładamy, że $\alpha$ (najlepsze rozwiązanie kandydujące), $\beta$ i $\delta$ posiadają większą wiedzę o potencjalnej lokalizacji ofiary. W związki z tym zapisujemy 3 najlepsze rozwiązania, które dotychczas otrzymaliśmy i obligujemy pozostałych agentów do aktualizacji ich pozycji na podstawie pozycji najlepszych agentów. (Liczone w każdej iteracji dla każdego agenta)

\begin{equation}
    \vec{D}_{\alpha} = |\vec{C}_1 \cdot \vec{X}_{\alpha} - \vec{X}|
\end{equation}
\begin{equation}
    \vec{D}_{\beta} = |\vec{C}_2 \cdot \vec{X}_{\beta} - \vec{X}|
\end{equation}
\begin{equation}
    \vec{D}_{\delta} = |\vec{C}_3 \cdot \vec{X}_{\delta} - \vec{X}|
\end{equation}

\begin{equation}
    \vec{X}_1 = \vec{X}_{\alpha} - \vec{A}_1 \cdot (\vec{D}_{\alpha})
\end{equation}
\begin{equation}
    \vec{X}_2 = \vec{X}_{\beta} - \vec{A}_1 \cdot (\vec{D}_{\beta})
\end{equation}
\begin{equation}
    \vec{X}_3 = \vec{X}_{\delta} - \vec{A}_1 \cdot (\vec{D}_{\delta})
\end{equation}

\begin{equation}
    \vec{X}(t + 1) = \frac{\vec{X}_1(t) + \vec{X}_2(t) + \vec{X}_3(t)}{3}
\end{equation}


Algorytm GWO przyjmuje, że "najlepszą" informację o położeniu ofiary posiadają wilki $\alpha$, $\beta$, $\delta$ i na podstawie ich położenia szacowane jest położenie ofiary - tak na prawdę na ich podstawie jest szacowany okrąg, w którym ta ofiara się znajduje.. Pozycja pozostałych wilków (agentów szukających) aktualizowana jest na podstawie pozycji $\alpha$, $\beta$, $\delta$. Wykorzystywany jest tutaj wcześniej opisany mechanizm otoczenia ofiary - nowa pozycja każdego z agentów to losowe miejsce w okół oszacowanej pozycji ofiary - czyli znajduje się w okręgu, który szacują $\alpha$, $\beta$, $\delta$.

Poniżej (wydaje mi się, że lepiej) opisany obrazek:


![Aktualizacja pozycji. Źródło \cite{gwo}](figures/fig2-v2.png){ width=50% }

### Atakowanie ofiary (eksploatacja)
Jak wcześniej wspomniano, wilki kończą polowanie atakiem ofiary, kiedy przestaje się ona poruszać (w rzeczywistości/naturalnym środowisku). Aby matematycznie zamodelować ten proces, zmniejszamy liniowo wartość $\vec{a}$. Należy zauważyć, że wartości $\vec{A}$ także zmniejszają się wraz z $\vec{a}$. W innych słowach wartości $\vec{A}$ są wartościami losowymi należącymi do przedziału $[-2\vec{a}, 2\vec{a}]$, gdzie wartości $\vec{a}$ zmniejszają się z 2 do 0 z każdą iteracją. Kiedy wartości $\vec{A}$ należą do $[-1, 1]$, następną pozycją agenta może być każda pozycja między aktualną pozycją, a pozycją ofiary. 

Dotąd zaproponowane operatory pozwalają agentom na aktualizację pozycji zgodnie z pozycjami $\alpha$, $\beta$, $\delta$ i atakować ofiarę. Jednakże algorytm ten nie jest odporny na stagnację w rozwiązaniach lokalnych.

![a) Atakowanie ofiary b) Poszukiwanie ofiary. Źródło \cite{gwo}](figures/fig3.png){ width=50% }

### Poszukiwanie ofiary (eksploracja)
Wilki zwykle poszukują ofiary zgodnie z pozycjami $\alpha$, $\beta$, $\delta$. Oddalają się od siebie w poszukiwaniu zdobyczy a później zbierają się by ją zaatakować. Aby matematycznie zamodelować oddalanie się wykorzystujemy losowe wartości $|\vec{A}| > 1$, aby wymusić na agencie oddalenie się od zdobyczy. Uwydatnia to proces eksploracji i pozwala GWO na globalne przeszukiwanie przestrzeni. Obrazek 3 również to pokazuje. 

Kolejnym komponente faworyzującym eksplorację jestt $\vec{C}$. Tak jak pokazuje \ref{eq:4}, $\vec{C}$ zawiera losowe wartosci z przedziału $[0, 2]$. Ten komponent zapewnia losowe wagi dla ofiary, aby stochastycznie podkreślić ($C > 1$) lub zmniejszyć znaczenie ($C < 1$) wpływ ofiary na definiowanie odległości w równaniu \ref{eq:1}. Pozwala to GWO na bardziej losowe zachowanie w trakcie optymalizacji, sprzyjające eksploracji i unikaniu lokalnych optimów. Warto tutaj zaznaczyć, że w porónaniu do $A$, $C$ nie jest liniowo zmniejszane. Celowo wymagamy, żeby $C$ zapewniało losowe wartości przez cały czas do podkreślenia eksploracji nie tylko podczas początkowych iteracji, ale także końcowych. Ten komponent jest bardzo przydatny w przypadku stagnacji (lokalne optima), w szczególności w ostatnich iteracjach.

Wektor $\vec{C}$ może być uważany za imitację przeszkód, które są napotykane podczas polowania. Ogólnie mówiąc, przeszkody w naturalny sposób pojawiają się i uniemożliwiają szybkie i wygodne podejście do ofiary. I właśnie za to odpowiada $\vec{C}$. W zależności od pozycji wilka, może losowo nadać ofiarze wagę i utrudnić wilkom dotarcie do niej i vice versa.

## Algorytm
Aby zobaczyć jak GWO jest teoretycznie w stanie rozwiązać probl
\begin{itemize}
    \item Zaproponowana hierarchia pomaga GWO w zapisaniu najlepszych dotąd otrzymanych rozwiązań w trakcie kolejnych iteracji
    \item Zaproponowany mechanizm okrążania definiuje sąsiedztwo w kształcie koła wokół rozwiązań, które może być rozwinięte do liczniejszych wymiarów jako hiper-sfery.
    \item Losowe wartosci $A$ i $C$ pomagają rozwiązaniom kandydującym do posiadania hiper-sfer z różnymi losowymi promieniami
    \item Zaproponowany mechanizm polowania pozwala rozwiązaniom kandydującym na zlokalizowanie prawdopodobnej pozycji ofiary
    \item Eksploracja i eksploatacja są zapewnione przez adaptacyjne wartości $a$ i $A$
    \item Adaptacyjne wartości $a$ i $A$ pozwalają GWO na płynne przejście między ekspolarcją i eksploatacją
    \item Ze wzrastającym $A$, połowa iteracji jest poświęcona eksploracji ($|A| \geq 1$) a druga połowa eksploatacji ($|A| < 1$)
    \item GWO posiada tylko 2 główne parametry, które wymagają dostrojenia - $a$ i $C$
\end{itemize}
\input{./scripts/GWO.tex}

# Warianty GWO
W związku ze złożoną naturą rzeczywistych problemów optymalizacyjnych, algorytm GWO został zmodyfikowany tak, aby odpowiadał przestrzeniom przeszukiwania złożonych dziedzin. Poniżej zaprezentowano kilka znaczniejszych modyfikacji, które zostały zebrane w artykule zbiorczym \cite{gwo-review}.

## Zmodyfikowane wersje GWO
### Mechanizm aktualizacji położenia
W tych badaniach zajęto się poprawą równowagi między eksploracją i eksploatacją. Badania podzieliły się na dwa kierunki: pierwsze z nich próbowało dynamicznie aktualizować parametry GWO, a drugi z nich porponuje różne strategie aktualizacji osobników. 
Najważniejsze propozycje to:

\begin{itemize}
\item W pracy \cite{mgwo-1} zaproponowano zmniejszanie wartości $\vec{a}$ z wykorzystaniem zanikającej funkcji wykładniczej, a nie liniowej:
\begin{equation}
    a = 2\Big(1 - \frac{iter^2}{maxIter^2}\Big)
\end{equation}
Autorzy przetestowali to podejście na 27 funkcjach testowych, a wyniki porównali z PSO, BA, CS i klasycznym GWO. Badania pokazały, że osiąnęli oni lepszą eksplorację.

\item W pracy \cite{mgwo-2} zaproponowano zmniejszanie wartości $\vec{a}$ nieliniowo, gdzie $\mu$ jest nieliniowym wektorem modulacji w interwale $(0, 3)$
\begin{equation}
	a = \Big(1 - \frac{iter}{maxIter}\Big) \cdot \Big(1 - \mu \frac{iter}{maxIter}\Big)^{-1}
\end{equation}
Przeprowadzone badania na licznych ograniczonych funkcjach pokazały, że tak zmodyfikowany GWO osiąga lepszą równowagę między eksploracją, a eksploatacją.

\item W pracy \cite{mgwo-3} zaproponowano aktualizację pozycji agentów oparta na wprowadzeniu kroku, który jest proporcjonalny do dostosowania agenta w obecnej iteracji. Plusem tej modyfikacji jest mniejsza liczba parametrów i brak konieczności inicjalizacji parametrów początkowych.
\begin{equation}
    X_i^{t+1} = \Big(\frac{1}{t}\Big)^{[(bestf(t)-f_i(t)) / (bestf(t) - worstf(t))]}
\end{equation}
Przeprowadzone badania na 27 funkcjach testowych pokazały, że tak zmodyfikowany GWO osiąga szybszą zbieżność, a wyniki są lepsze w porównaniu do klasycznego GWO.
\end{itemize}

### Nowe operatory
\begin{itemize}
\item W pracy \cite{mgwo-4} autorzy wprowadzili prosty operator krzyżowania między dwoma losowymi agentami. Jego celem jest dzielenie się informacjami w populacji. Przeprowadzone badania na 6 funkcjach testowych i porównanie wyników z klasycznym GWO pokazały, że operator krzyżowania ulepsza działanie, jakość rozwiązania i prędkość zbieżności. 

\item W pracy \cite{mgwo-5} wprowadzono kolejny typ operatora ewolucyjna dynamika populacji (ang. evolutionary population dynamics, EPD). Został on wprowadzony do eliminacji najgorszych osobników w populacji i zmiany ich pozycji w pobliżu wilków dowodzących. Autorzy twierdzą, że wprowadzenie tego operatora ulepsza medianę populacji w kolejnych generacjach oraz ma pozytywny wpływa na eksplorację i eksploatację. Porówanie wyników dla 13 funkcji testowych potwierdzają zalety tego wariantu.

\item W pracy \cite{mgwo-6} zintegrowano algorytm optymalizacyjny Powell'a jako operator lokalnego przeszukiwania, a modyfikację nazwano PGWO. Algorytm Powell'a jest metodą znajdowania minimum funkcji, które nie musi być różniczkowalne, a pochodne nie są konieczne. Taka modyfikacja sukcesywnie wykonuje dwukierunkowe przeszukiwanie wśród rozwiązań kandydujących. Algorytm został przetestowany tylko na 7 funkcjach. Wyniki porównano z GWO, GA, PSO, GGSA, ABC i CS. Pokazały one poprawę działąnia w porównaniu do GWO i bardzo konurencyjne wyniki w porównaniu do reszty algorytmów.
\end{itemize}

### Kodowanie
W pracy \cite{mgwo-7} zaproponowano zmianę kodowania osobników. Zamiast typowego kodowania rzeczywistego wykorzystano kodowanie zespolone. Agent posiada gen o dwóch częściach - część rzeczywistą oraz urojoną. Autorzy twierdzą, że taka reprezentacja może rozszerzyć pojemność informacji oraz zwiększyć różnorodność populacji. Rezultaty porównano z klasycznym GWO, ABC i GGSA. Na podstawie 6 funkcji testowych pokazano, że zaproponowana wersja algorytmu posiada bardzo konkurencyjne wyniki.

### Modyfikacja struktury populacji oraz hierarchii
GWO posiada unikatową hierarchiczną strukturę populacji. Tak jak wcześniej wyjaśniono, istnieją 4 różne typy osobników. Trzy z nich posiada tylko jednego reprezentanta, a reszta osobników należy do czwartego typu. Ta wyróżniająca struktura zmotywowała badaczy do zbadania, czy zmiana w strukturze będzie miała jakieś efekty. W pracy \cite{mgwo-8} zaproponowano inną hierarchię przywództwa. Populacja jest podzielona na dwie niezależne subpopulacje. Pierwsza z nich nazwana jest kooperatywną grupą polującą, a druga losową grupą zwiadowczą. Zadaniem grupy zwiadowczej jest przeprowadzenie szerokiej eksploracji, natomiast grupy polującej jest wykonanie głębokiej eksploatację. W odróżnieniu od klasycznego GWO, wilki $\delta$ podzielone są na dwa typy: $\delta_1$, który reprezentuje wilki-łowców i $\delta_2$, które reprezentują wilki-zwiadowców. Autorzy tej modyfikacji zastosowali ją do dostrojenia parametrów sterowników generatora indukcyjnego opartego na turbinie wiatrowej. Wyniki porównali z wynikami otrzymanymi z GA, PSO, GWO i MFO. Ich wyniki pokazały lepsze wartości dopasowania i wyższą stabilność.

## Hybrydy GWO
Ogólnie mówiąc hybrydyzacja metaheurystyk polega na połączeniu ze sobą co najmniej dwóch algorytmów, aby uwydatnić i wykorzystać mocne strony każdego z nich. W literaturze można znaleźć przykład hybrydyzacji GWO z PSO w stylu sekwencyjnym do optymalizacji problemu o jednostkowy obszarze \cite{mgwo-9}. Wygenerowane wyniki były konkurencyjne, jednakże to podejście posiada wolniejszą zbieżność z powodu długiego, sekwencyjnego czasu wykonywania. Inna propozycja to połączenie GWO z DE. Dwie prace pokazały bardzo konkurencyjne wyniki w porównaniu do DE czy PSO \cite{mgwo-10-1} \cite{mgwo-10-2}.

## Paralelny GWO
Paralelizm może być efektywnie stosowany w metaheurystykach opartych na populacji. Populacja może zostać podzielona na mniejsze, a każda z nich może ewoluować na innym procesorze maszyny. Paralelizm algorytmu może efektywnie zmniejszyć czas wykonywania algorytmu oraz poprawić jakość rozwiązania. W jednej z prac zaproponowano paralelną wersję GWO, gdzie podgrupy ewoluują niezależnie i co określoną liczbę iteracji wymieniają się miedzy sobą najlepszymi rozwiązaniami. \cite{mgwo-11} Badania na 4 różnych funkcjach testowych pokazały, ze zmodyfikowana wersja algorytmu poprawia znacząco wydajność pod względem jakości rozwiązania oraz czasu wykonywania.

# Zastosowanie GWO
W związku z impresywnymi zaletami GWO, algorytm został wykorzystany do rozwiązania wielu różnorodnych problemów. Poniższy diagram przedstawia zastosowanie GWO w różnych dziedzinach w czerwcu 2017r.

![Zastosowanie GWO w różnych dziedzinach. Źródło \cite{gwo-review}](figures/fig5.png){ width=50% }

## Uczenie maszynowe
Można znaleźć zastosowanie w takich dziedzinach uczenia maszynowego jak:
\begin{itemize}
\item Ekstrakcja cech
\item Trenowanie sieci neuronowych
\item Optymalizacja SVM
\item Klasteryzacja
\end{itemize}

## Problemy inżynierskie
Algorytm GWO znalazł zastosowanie w między innymi:
\begin{itemize}
\item Projektowaniu i dostrajaniu sterowników
\item Problemach dotyczących przesyłanie energii elektrycznej
\item Robotyka i planowanie ścieżek
\item Planowanie harmonogramów
\end{itemize}

## Pozostałe dziedziny
Znalazł zastosowanie także w:
\begin{itemize}
\item Bezprzewodowej sieci czujników
\item Modelowaniu środowiska
\item medycynie i bioinformatyce
\end{itemize}

# Wielokryterialny GWO (MOGWO)
Algorytm GWO jest w stanie rozwiązać problem o jednym kryterium. Autor tego algorytmu zaproponował jego wielokryterialną wersję do rozwiązywania problemów posiadających wiele kryteriów \cite{mogwo}. MOGWO korzysta z tego samego mechanizmu aktualizującego położenia. W związku z istnieniem wielu najlepszych rozwiązań - tak zwanych rozwiązań Pareto optymalnych - wprowadzono kilka modyfikacji.

## Wprowadzone modyfikacje
Wprowadzono archiwum do zarządzania rozwiązaniami Pareto optymalnymi. Jest nie tylko magazynem, ale także selektorem osobników $\alpha$, $\beta$, $\delta$. Kontroler archiwum wybiera lidera z mniej zaludnionych regionów oszacowanego optymalnego frontu Pareto i zwraca je jako $\alpha$, $\beta$, $\delta$. Ten mechanizm został zaprojektowany, aby poprawić rozmieszczenie (pokrycie) optymalnych rozwiązań Pareto dla wszystkich kryteriów. 

![Selekcja lidera z najmniej zaludnionych obszarów. Źródło \cite{gwo-review}](figures/fig4.png){ width=50% }

Kolejnym mechanizmem poprawy rozmieszczenia rozwiązań jest proces czyszczenia archiwum. Kiedy archiwum jest przepełnione, rozwiązania powinny zostać usunięte tak, aby pomieścić nowe niezdominowane rozwiązania. MOGWO usuwa rozwiązania z najbardziej zaludnionych regionów. Dzięki temu prawdopodobieństwo poprawy rozwiązań rośnie wraz z kolejnymi iteracjami. 

Zbieżność MOGWO jest podobna do GWO z powodu wykorzystania tego samego mechnizmu aktualizacji pozycji. Rozwiązania napotykają nagłe zmiany w pierwszej połowie iteracji i stopniowe fluktuacje w pozostałej części. Ze względu na wybór $\alpha$, $\beta$, $\delta$ z najsłabiej zaludnionych regionów, wybrani liderzy będą pochodzili z różnych regionów. Pogarsza to zbieżność w kierunku jednego najlepszego rozwiązania, ale jednak jest to wymagane, aby utrzymać rozkład rozwiązań zgodnie z kryteriami.

Poniżej "wytłumaczono" te mechanizmy na podstawie publikacji \cite{mopso}.

### Archiwum
Zadaniem archiwum jest przechowywanie niezdominowanych rozwiązań, zwanych populacją zewnętrzną. Są to najlepsze dotąd znalezione rozwiązania.

### Kontroler archiwum
Zadaniem kontrolera archiwum jest podjęcie decyzji, czy obecne rozwiązanie powinno zostać dodane do archiwum. Czynność ta wygląda w każdej iteracji następująco :

\input{./scripts/MOGWO-kontroler.tex}

### Siatka
Algorytm wykorzystuje siatkę adaptacyjną, aby front Pareto został równomiernie rozmieszczony. Podstawową ideą jest użycie archiwum do przechowywania paretooptymalnych rozwiązań z wzięciem pod uwagę jego zawartości. W archiwum przestrzeń jest podzielona na regiony, tak jak na rysunku \ref{fig:grid1}. Jeżeli dodawany osobnik leży poza obecnymi granicami siatki, należy ją zaktualizować. 
Siatka adaptacyjna tak naprawdę jest przestrzenią uformowaną przez hiperkostki, które posiadają tyle wymiarów, ile jest funkcji kryterialnych. 

\begin{figure}
    \centering
    \subfloat[Siatka przed dodaniem osobnika]{{\includegraphics[width=5.5cm]{figures/grid-1.png} }}%
    \qquad
    \subfloat[Siatka po dodaniu osobnika]{{\includegraphics[width=5.5cm]{figures/grid-2.png} }}%
    \caption{Graficzna reprezentacja dodawania nowego osobnika, który znajduje się w granicach siatki}\label{fig:grid1}
\end{figure}

\begin{figure}
    \centering
    \subfloat[Siatka przed dodaniem osobnika]{{\includegraphics[width=5.5cm]{figures/grid-1.png} }}%
    \qquad
    \subfloat[Siatka po dodaniu osobnika]{{\includegraphics[width=5.5cm]{figures/grid-3.png} }}%
    \caption{Graficzna reprezentacja dodawania nowego osobnika, który znajduje się poza granicami siatki}\label{fig:grid2}
\end{figure}

### Selekcja najlepszego rozwiązania
Selekcja najlepszego osobnika w archiwum odbywa się w dwóch krokach:
\begin{enumerate}
    \item Selekcja hiperkostki z wykorzystaniem selekcji ruletkowej. \\
    Do kalibracji ruletki wykorzystujemy poniższe prawdopodobieństwo dla każdej hiperkostki:
    
    \begin{equation*}
        P_i = \frac{c}{N_i}, 
    \end{equation*}
    gdzie $c$ to stała liczba większa od $1$ oraz $N_i$ to liczba rozwiązań należących do danej hiperkostki.
    
    \item Selekcja losowego osobnika z wybranej kostki.
\end{enumerate}

## Algorytm
\input{./scripts/MOGWO.tex}

## Zastosowanie MOGWO
### Robotyka i planowanie ścieżki
W pracy \cite{mogwo-1} autorzy wykorzystali MOGOW do optymalizacji planowania ścieżki dla robota. Użyto dwóch kryterów do minimalizacji, które odpowiadają za długość oraz płynność ścieżki. Przeprowadzono wiele simulacji w różnych statycznych środowiskach. Wyniki pokazują, że zaproponowane podejście poprawnie zapewnia optymalną ścieżkę do celu bez kolidowania z przeszkodami.

### Planowanie
W pracy \cite{mogwo-2} autorzy zaproponowali dyskretną wersję MOGWO do optymalizacji planowania rzeczywistego procesu spawania. Jako kryteria, które należy zminimalizaować, wybrano długość trwania całego procesu oraz obciążenie maszyn. Badania i testy statystyczne pokazały przewagę zaproponowanego rozwiązania nad NSGA-II i SPEA2.

### Bezprzewodowa sieć czujników
W pracy \cite{mogwo-3} autorzy zaproponowali MOGOW do rozwiązywania problemu lokalizacji wierzchołków. Zaznaczyli oni, że podejście wielokryterialne może być bardziej wydajny w porównaniu do klasycznego podejścia jednokryterialnego. Wykorzystali oni dwa kryteria - odległość wierzchołków oraz geometrię topologiczną. Badania pokazały, że w ten sposób można efektywnie zmniejszyć średni błąd lokalizacji.

\newpage
\input{./scripts/bibliography.tex}
