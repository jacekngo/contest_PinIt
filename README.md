Program zapisuje wybrane dane z obiektu klasy Location i wyświetla w formie listy. Do zapisu użyłem bazy danych Realm ze względu na jej szybkość i prostotę – kierując się m.in. opiniami na stronie Droidsa. 

Do wyświetlania danych użyłem adaptera z recyclerView zamiast realm-owego – bo podobno (wg . prowadzącego zajęcia z programowania na które chodziłem) tych ostatnich nie stosuje się z praktyce, bo są źle napisane i ograniczają możliwość rozbudowy. Trochę żałuję (tym bardziej, że nie znalazłem potwierdzenia tej opinii), bo zabijając realma zafundowałem aplikacji  niepotrzebne 2n problemu z walidacją listy (przypisaniem kolejności do primary key obiektu) i zapisem do bazy danych.    
  
Aplikacja posiada podstawową założoną funkcjonalność – zbiera lokalizację i pozwala na dodanie notatki, edycję, usunięcie i wyświetlenie na mapie. Notatka jest podzielona na dwa pola tytuł (lead, który wyświetla się w liście) i opis. Kolor notatki zmienia się w zależności od tego czy pole z opisem jest puste.  

Przesuwanie i edycja są oparte na przyciskach znajdujących się w viewHolderze .  
Ponieważ aplikacja jest nieskomplikowana logika została umieszczona w klasie MainActivity. 