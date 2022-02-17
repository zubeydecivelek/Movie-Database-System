import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //OPENING AN EMPTY OUTPUT FILE
        BufferedWriter outputt = new BufferedWriter(new FileWriter(args[3]));
        outputt.close();

        //READING PEOPLE FILE AND SAVING THEM IN LIST
        BufferedReader peopleFile = new BufferedReader(new FileReader(args[0]));
        ArrayList<String[]> peopleList = new ArrayList<>();
        String line;
        while ((line = peopleFile.readLine()) != null) {
            peopleList.add(line.split("\t"));
        }
        peopleFile.close();

        //SAVING PEOPLE AS AN OBJECTS
        ArrayList<Performer> allPerformerObjectList = new ArrayList<>();
        ArrayList<User> userObjectsList = new ArrayList<>();
        ArrayList<Actor> actorObjectsList = new ArrayList<>();
        ArrayList<ChildActor> childActorObjectsList = new ArrayList<>();
        ArrayList<StundPerformer> stundPerformerObjectsList = new ArrayList<>();
        ArrayList<Director> directorObjectsList = new ArrayList<>();
        ArrayList<Writer> writerObjectsList = new ArrayList<>();
        for (String[] i : peopleList) {
            if (i[0].startsWith("User")) {
                User user = new User(i[1], i[2], i[3], i[4]);
                userObjectsList.add(user);
            } else if (i[0].startsWith("Actor")) {
                Actor actor = new Actor(i[1], i[2], i[3], i[4], i[5]);
                actorObjectsList.add(actor);
                allPerformerObjectList.add(actor);
            } else if (i[0].startsWith("ChildActor")) {
                ChildActor childActor = new ChildActor(i[1], i[2], i[3], i[4], i[5]);
                childActorObjectsList.add(childActor);
                allPerformerObjectList.add(childActor);
            } else if (i[0].startsWith("StuntPerformer")) {
                ArrayList<String> realActorId = new ArrayList<>(Arrays.asList(i[6].split(",")));
                StundPerformer stundPerformer = new StundPerformer(i[1], i[2], i[3], i[4], i[5], realActorId);
                stundPerformerObjectsList.add(stundPerformer);
                allPerformerObjectList.add(stundPerformer);
            } else if (i[0].startsWith("Director")) {
                Director director = new Director(i[1], i[2], i[3], i[4], i[5]);
                directorObjectsList.add(director);
            } else if (i[0].startsWith("Writer")) {
                Writer writer = new Writer(i[1], i[2], i[3], i[4], i[5]);
                writerObjectsList.add(writer);
            } else {
                System.out.println("Invalid people...");
            }
        }

        //READING FILMS FILE AND SAVING THEM IN LIST
        BufferedReader filmFile = new BufferedReader(new FileReader(args[1]));
        ArrayList<String[]> filmsList = new ArrayList<>();
        String line1;
        while ((line1 = filmFile.readLine()) != null) {
            filmsList.add(line1.split("\t"));
        }
        filmFile.close();

        //SAVING FILMS AS AN OBJECTS
        ArrayList<Film> allFilmsObjectList = new ArrayList<>();
        ArrayList<FeatureFilm> featureFilmsObjectList = new ArrayList<>();
        ArrayList<ShortFilm> shortFilmsObjectList = new ArrayList<>();
        ArrayList<Documentary> documentaryObjectList = new ArrayList<>();
        ArrayList<TvSeries> tvSeriesObjectList = new ArrayList<>();
        for (String[] i : filmsList) {
            ArrayList<String> directorIDs = new ArrayList<>(Arrays.asList(i[4].split(",")));
            ArrayList<String> performers = new ArrayList<>(Arrays.asList(i[7].split(",")));
            if (i[0].startsWith("FeatureFilm") || i[0].startsWith("ShortFilm")) {
                ArrayList<String> writerIDs = new ArrayList<>(Arrays.asList(i[10].split(",")));
                if (i[0].startsWith("FeatureFilm")) {
                    FeatureFilm featureFilm = new FeatureFilm(i[1], i[2], i[3], directorIDs, i[5], i[6], performers, i[8], i[9], writerIDs, i[11]);
                    featureFilmsObjectList.add(featureFilm);
                    allFilmsObjectList.add(featureFilm);
                } else {
                    if (Integer.parseInt(i[5]) > 40) {
                        System.out.println("Short Film's runtime should be less (or equal) than 40 min.");
                    } else {
                        ShortFilm shortFilm = new ShortFilm(i[1], i[2], i[3], directorIDs, i[5], i[6], performers, i[8], i[9], writerIDs);
                        shortFilmsObjectList.add(shortFilm);
                        allFilmsObjectList.add(shortFilm);
                    }
                }
            } else if (i[0].startsWith("Documentary")) {
                Documentary documentary = new Documentary(i[1], i[2], i[3], directorIDs, i[5], i[6], performers, i[8]);
                documentaryObjectList.add(documentary);
                allFilmsObjectList.add(documentary);
            } else if (i[0].startsWith("TVSeries")) {
                ArrayList<String> writers = new ArrayList<>(Arrays.asList(i[9].split(",")));
                TvSeries tvSeries = new TvSeries(i[1], i[2], i[3], directorIDs, i[5], i[6], performers, i[8], writers, i[10], i[11], i[12], i[13]);
                tvSeriesObjectList.add(tvSeries);
                allFilmsObjectList.add(tvSeries);
            } else {
                System.out.println("Invalid film...");
            }
        }

        //READING COMMAND FILE AND SAVING THEM IN LIST
        BufferedReader commandFile = new BufferedReader(new FileReader(args[2]));
        ArrayList<String[]> commandList = new ArrayList<>();
        String line2;
        while ((line2 = commandFile.readLine()) != null) {
            commandList.add(line2.split("\t"));
        }
        commandFile.close();

        //APPLYING THE COMMANDS
        BufferedWriter outputFile = new BufferedWriter(new FileWriter(args[3], true));
        for (String[] i : commandList) {
            for (String j : i) {
                outputFile.write(j + "\t");
            }
            outputFile.write("\n\n");

            if (i[0].equals("RATE")) {
                User rateuser = null;
                for (User user : userObjectsList) {
                    if (user.getId().equals(i[1])) {
                        rateuser = user;
                        break;
                    }
                }
                Film rateFilm = null;
                for (Film film:allFilmsObjectList){
                    if (film.getFilmID().equals(i[2])){
                        rateFilm = film;
                        break;
                    }
                }
                if (rateuser == null || rateFilm == null) {
                    outputFile.write("Command Failed\nUser ID: " + i[1] + "\n" + "Film ID: " + i[2] + "\n");
                    outputFile.write("\n-----------------------------------------------------------------------------------------------------\n");
                    continue;
                } else {
                    if (rateuser.getRateDict().containsKey(rateFilm.getFilmTitle())) {
                        outputFile.write("This film was earlier rated\n");
                    } else {
                        if (Integer.parseInt(i[3]) < 1 || Integer.parseInt(i[3]) > 10) {
                            System.out.println("Rating score must be between 1 and 10.");
                        } else {
                            rateuser.Rate(rateFilm.getFilmTitle(), Integer.parseInt(i[3]));
                            rateFilm.Rate(Integer.parseInt(i[3]));
                            Class typeFilm = rateFilm.getClass();
                            outputFile.write("Film rated successfully\nFilm type: " + typeFilm.getName() + "\n" + "Film title: " + rateFilm.getFilmTitle() + "\n");
                        }
                    }
                }
            } else if (i[0].equals("ADD")) {
                if (!i[1].equals("FEATUREFILM")) {
                    System.out.println("You can add only Feature Film");
                    continue;
                }
                FeatureFilm addFilm = null;
                for (FeatureFilm featureFilm : featureFilmsObjectList) {
                    if (featureFilm.getFilmID().equals(i[2])) {
                        addFilm = featureFilm;
                        break;
                    }
                }
                if (addFilm == null) {
                    ArrayList<String> addDirectorIDs = new ArrayList<>(Arrays.asList(i[5].split(",")));
                    ArrayList<String> addPerformers = new ArrayList<>(Arrays.asList(i[8].split(",")));
                    ArrayList<String> addWriters = new ArrayList<>(Arrays.asList(i[11].split(",")));

                    ArrayList<String> directorIDsList = new ArrayList<>();
                    for (Director director : directorObjectsList) {
                        directorIDsList.add(director.getId());
                    }
                    ArrayList<String> performersList = new ArrayList<>();
                    for (Performer performer:allPerformerObjectList){
                        performersList.add(performer.getId());
                    }
                    ArrayList<String> writerIDsList = new ArrayList<>();
                    for (Writer writer : writerObjectsList) {
                        writerIDsList.add(writer.getId());
                    }
                    if (directorIDsList.containsAll(addDirectorIDs) && performersList.containsAll(addPerformers) && writerIDsList.containsAll(addWriters)) {
                        FeatureFilm featureFilm = new FeatureFilm(i[2], i[3], i[4], addDirectorIDs, i[6], i[7], addPerformers, i[9], i[10], addWriters, i[12]);
                        featureFilmsObjectList.add(featureFilm);
                        allFilmsObjectList.add(featureFilm);
                        outputFile.write("FeatureFilm added successfully\nFilm ID: " + featureFilm.getFilmID() + "\n" + "Film title: " + featureFilm.getFilmTitle() + "\n");
                    } else {
                        outputFile.write("Command Failed\nFilm ID: " + i[2] + "\n" + "Film title: " + i[3] + "\n");
                    }
                } else {
                    outputFile.write("Command Failed\nFilm ID: " + i[2] + "\n" + "Film title: " + i[3] + "\n");
                }
            } else if (i[0].equals("VIEWFILM")) {
                Film viewFilm = null;
                for (Film film:allFilmsObjectList){
                    if (film.getFilmID().equals(i[1])){
                        viewFilm = film;
                        break;
                    }
                }
                if (viewFilm instanceof FeatureFilm) {
                    outputFile.write(viewFilm.getFilmTitle() + " (" + ((FeatureFilm) viewFilm).getReleaseDate().substring(6) + ")\n" + ((FeatureFilm) viewFilm).getGenres() + "\nWriters: ");
                    for (String filmWriterID : ((FeatureFilm) viewFilm).getWriterIDs()) {
                        for (Writer writer : writerObjectsList) {
                            if (filmWriterID.equals(writer.getId())) {
                                outputFile.write(writer.getName() + " " + writer.getSurname());
                                if (!filmWriterID.equals(((FeatureFilm) viewFilm).getWriterIDs().get(((FeatureFilm) viewFilm).getWriterIDs().size() - 1)) && ((FeatureFilm) viewFilm).getWriterIDs().size() != 1) {
                                    outputFile.write(", ");
                                }
                                break;
                            }
                        }
                    }
                    outputFile.write("\nDirectors: ");
                    for (String filmDirectorID : ((FeatureFilm) viewFilm).getDirectorIDs()) {
                        for (Director director : directorObjectsList) {
                            if (filmDirectorID.equals(director.getId())) {
                                outputFile.write(director.getName() + " " + director.getSurname());
                                if (!filmDirectorID.equals(((FeatureFilm) viewFilm).getDirectorIDs().get(viewFilm.getDirectorIDs().size() - 1)) && viewFilm.getDirectorIDs().size() != 1) {
                                    outputFile.write(", ");
                                }
                                break;
                            }
                        }
                    }
                    outputFile.write("\nStars: ");
                    for (String performerId : viewFilm.getCast_performers()) {
                        for (Performer performer:allPerformerObjectList){
                            if(performerId.equals(performer.getId())){
                                outputFile.write(performer.getName() + " " + performer.getSurname());
                                if (!performerId.equals(((FeatureFilm) viewFilm).getCast_performers().get(viewFilm.getCast_performers().size() - 1)) && viewFilm.getCast_performers().size() != 1) {
                                    outputFile.write(", ");
                                }
                                break;
                            }
                        }
                    }
                    if (viewFilm.getRatingList().isEmpty()) {
                        outputFile.write("\nAwaiting for votes");
                    } else {
                        outputFile.write("\nRatings: " + viewFilm.AverageRateString(viewFilm) + "/10 from " + viewFilm.getRatingList().size() + " users\n");
                    }
                } else if (viewFilm instanceof ShortFilm) {
                    outputFile.write(viewFilm.getFilmTitle() + " (" + ((ShortFilm) viewFilm).getReleaseDate().substring(6) + ")\n" + ((ShortFilm) viewFilm).getGenres() + "\nWriters: ");
                    for (String filmWriterID : ((ShortFilm) viewFilm).getWriterIDs()) {
                        for (Writer writer : writerObjectsList) {
                            if (filmWriterID.equals(writer.getId())) {
                                outputFile.write(writer.getName() + " " + writer.getSurname());
                                if (!filmWriterID.equals(((ShortFilm) viewFilm).getWriterIDs().get(((ShortFilm) viewFilm).getWriterIDs().size() - 1)) && ((ShortFilm) viewFilm).getWriterIDs().size() != 1) {
                                    outputFile.write(", ");
                                }
                                break;
                            }
                        }
                    }
                    outputFile.write("\nDirectors: ");
                    for (String filmDirectorID : ((ShortFilm) viewFilm).getDirectorIDs()) {
                        for (Director director : directorObjectsList) {
                            if (filmDirectorID.equals(director.getId())) {
                                outputFile.write(director.getName() + " " + director.getSurname());
                                if (!filmDirectorID.equals(((ShortFilm) viewFilm).getDirectorIDs().get(viewFilm.getDirectorIDs().size() - 1)) && viewFilm.getDirectorIDs().size() != 1) {
                                    outputFile.write(", ");
                                }
                                break;
                            }
                        }
                    }
                    outputFile.write("\nStars: ");
                    for (String performerId : viewFilm.getCast_performers()) {
                        for (Performer performer:allPerformerObjectList){
                            if(performerId.equals(performer.getId())){
                                outputFile.write(performer.getName() + " " + performer.getSurname());
                                if (!performerId.equals(((ShortFilm) viewFilm).getCast_performers().get(viewFilm.getCast_performers().size() - 1)) && viewFilm.getCast_performers().size() != 1) {
                                    outputFile.write(", ");
                                }
                                break;
                            }
                        }
                    }
                    if (viewFilm.getRatingList().isEmpty()) {
                        outputFile.write("\nAwaiting for votes");
                    } else {
                        outputFile.write("\nRatings: " + viewFilm.AverageRateString(viewFilm) + "/10 from " + viewFilm.getRatingList().size() + " users\n");
                    }
                } else if (viewFilm instanceof Documentary) {
                    outputFile.write(viewFilm.getFilmTitle() + " (" + ((Documentary) viewFilm).getReleaseDate().substring(6) + ")\n");
                    outputFile.write("\nDirectors: ");
                    for (String filmDirectorID : ((Documentary) viewFilm).getDirectorIDs()) {
                        for (Director director : directorObjectsList) {
                            if (filmDirectorID.equals(director.getId())) {
                                outputFile.write(director.getName() + " " + director.getSurname());
                                if (!filmDirectorID.equals(((Documentary) viewFilm).getDirectorIDs().get(viewFilm.getDirectorIDs().size() - 1)) && viewFilm.getDirectorIDs().size() != 1) {
                                    outputFile.write(", ");
                                }
                                break;
                            }
                        }
                    }
                    outputFile.write("\nStars: ");
                    for (String performerId : viewFilm.getCast_performers()) {
                        for (Performer performer:allPerformerObjectList){
                            if(performerId.equals(performer.getId())){
                                outputFile.write(performer.getName() + " " + performer.getSurname());
                                if (!performerId.equals(((Documentary) viewFilm).getCast_performers().get(viewFilm.getCast_performers().size() - 1)) && viewFilm.getCast_performers().size() != 1) {
                                    outputFile.write(", ");
                                }
                                break;
                            }
                        }
                    }
                    if (viewFilm.getRatingList().isEmpty()) {
                        outputFile.write("\nAwaiting for votes");
                    } else {
                        outputFile.write("\nRatings: " + viewFilm.AverageRateString(viewFilm) + "/10 from " + viewFilm.getRatingList().size() + " users\n");
                    }
                } else if (viewFilm instanceof TvSeries) {
                    outputFile.write(viewFilm.getFilmTitle() + " (" + ((TvSeries) viewFilm).getStartDate().substring(6) + "-" + ((TvSeries) viewFilm).getEndDate().substring(6) + ")\n" + ((TvSeries) viewFilm).getSeasons() + " seasons, " + ((TvSeries) viewFilm).getEpisodes() + " episodes\n" + ((TvSeries) viewFilm).getGenres() + "\nWriters: ");
                    for (String filmWriterID : ((TvSeries) viewFilm).getWriterIDs()) {
                        for (Writer writer : writerObjectsList) {
                            if (filmWriterID.equals(writer.getId())) {
                                outputFile.write(writer.getName() + " " + writer.getSurname());
                                if (!filmWriterID.equals(((TvSeries) viewFilm).getWriterIDs().get(((TvSeries) viewFilm).getWriterIDs().size() - 1)) && ((TvSeries) viewFilm).getWriterIDs().size() != 1) {
                                    outputFile.write(", ");
                                }
                                break;
                            }
                        }
                    }
                    outputFile.write("\nDirectors: ");
                    for (String filmDirectorID : ((TvSeries) viewFilm).getDirectorIDs()) {
                        for (Director director : directorObjectsList) {
                            if (filmDirectorID.equals(director.getId())) {
                                outputFile.write(director.getName() + " " + director.getSurname());
                                if (!filmDirectorID.equals(((TvSeries) viewFilm).getDirectorIDs().get(viewFilm.getDirectorIDs().size() - 1)) && viewFilm.getDirectorIDs().size() != 1) {
                                    outputFile.write(", ");
                                }
                                break;
                            }
                        }
                    }
                    outputFile.write("\nStars: ");
                    for (String performerId : viewFilm.getCast_performers()) {
                        for (Performer performer:allPerformerObjectList){
                            if(performerId.equals(performer.getId())){
                                outputFile.write(performer.getName() + " " + performer.getSurname());
                                if (!performerId.equals(((TvSeries) viewFilm).getCast_performers().get(viewFilm.getCast_performers().size() - 1)) && viewFilm.getCast_performers().size() != 1) {
                                    outputFile.write(", ");
                                }
                                break;
                            }
                        }
                    }
                    if (viewFilm.getRatingList().isEmpty()) {
                        outputFile.write("\nAwaiting for votes");
                    } else {
                        outputFile.write("\nRatings: " + viewFilm.AverageRateString(viewFilm) + "/10 from " + viewFilm.getRatingList().size() + " users\n");
                    }
                } else {
                    outputFile.write("Command Failed\nFilm ID: " + i[1]);
                }
            } else if (i[0].equals("LIST")) {
                if (i[1].equals("USER")) {
                    User listuser = null;
                    for (User user : userObjectsList) {
                        if (user.getId().equals(i[2])) {
                            listuser = user;
                            break;
                        }
                    }
                    if (listuser == null) {
                        outputFile.write("Command Failed\nUser ID: " + i[2] + "\n");
                    }
                    else if (listuser.getRateDict().isEmpty()) {
                        outputFile.write("There is not any ratings so far\n");
                    }
                    else {
                        for (Map.Entry rate : listuser.getRateDict().entrySet()) {
                            outputFile.write(rate.getKey() + ": " + rate.getValue() + "\n");
                        }
                    }
                } else if (i[1].equals("FILM") && i[2].equals("SERIES")) {
                    if (tvSeriesObjectList.isEmpty()) {
                        outputFile.write("No result\n");
                    } else {
                        for (TvSeries tvSeries : tvSeriesObjectList) {
                            outputFile.write(tvSeries.getFilmTitle() + " (" + tvSeries.getStartDate().substring(6) + "-" + tvSeries.getEndDate().substring(6) + ")" + "\n" + tvSeries.getSeasons() + " seasons and " + tvSeries.getEpisodes() + " episodes\n\n");
                        }
                        outputFile.write("-----------------------------------------------------------------------------------------------------\n");
                        continue;
                    }
                } else if (i[1].equals("FILMS") && i[3].equals("COUNTRY")) {
                    ArrayList<Film> films = new ArrayList<>();
                    for (Film film:allFilmsObjectList){
                        if(film.getCountry().equals(i[4])){
                            films.add(film);
                        }
                    }
                    if (films.isEmpty()) {
                        outputFile.write("No result\n");
                    } else {
                        for (Film film : films) {
                            outputFile.write("Film title: " + film.getFilmTitle() + "\n" + film.getRuntime_length() + " min\nLanguage: " + film.getLanguage() + "\n\n");
                        }
                        outputFile.write("-----------------------------------------------------------------------------------------------------\n");
                        continue;
                    }
                } else if (i[2].equals("BEFORE") || i[2].equals("AFTER")) {
                    ArrayList<FeatureFilm> films = new ArrayList<>();
                    for (FeatureFilm featureFilm : featureFilmsObjectList) {
                            if (i[2].equals("BEFORE") && Integer.parseInt(featureFilm.getReleaseDate().substring(6)) < Integer.parseInt(i[3])) {
                                films.add(featureFilm);
                            }
                            else if (i[2].equals("AFTER") && Integer.parseInt(featureFilm.getReleaseDate().substring(6)) >= Integer.parseInt(i[3])) {
                                films.add(featureFilm);
                            }
                        }
                    if (films.isEmpty()) {
                        outputFile.write("No result\n");
                    } else {
                        for (FeatureFilm printFilm : films) {
                            outputFile.write("Film title: " + printFilm.getFilmTitle() + " (" + printFilm.getReleaseDate().substring(6) + ")\n" + printFilm.getRuntime_length() + " min\nLanguage:" + printFilm.getLanguage() + "\n\n");
                        }
                        outputFile.write("-----------------------------------------------------------------------------------------------------\n");
                        continue;
                    }
                } else if (i[1].equals("ARTISTS")) {
                    outputFile.write("Directors:\n");
                    ArrayList<Director> countryDirector = new ArrayList<>();
                    for (Director director : directorObjectsList) {
                        if (director.getCountry().equals(i[3])) {
                            countryDirector.add(director);
                        }
                    }
                    if (countryDirector.isEmpty()) {
                        outputFile.write("No result\n");
                    } else {
                        for (Director printDirector : countryDirector) {
                            outputFile.write(printDirector.getName() + " " + printDirector.getSurname() + " " + printDirector.getAgent() + "\n");
                        }
                    }
                    outputFile.write("\nWriters:\n");
                    ArrayList<Writer> countryWriter = new ArrayList<>();
                    for (Writer writer : writerObjectsList) {
                        if (writer.getCountry().equals(i[3])) {
                            countryWriter.add(writer);
                        }
                    }
                    if (countryWriter.isEmpty()) {
                        outputFile.write("No result\n");
                    } else {
                        for (Writer printWriter : countryWriter) {
                            outputFile.write(printWriter.getName() + " " + printWriter.getSurname() + " " + printWriter.getWritingStyle() + "\n");
                        }
                    }
                    outputFile.write("\nActors:\n");
                    ArrayList<Actor> countryActor = new ArrayList<>();
                    for (Actor actor : actorObjectsList) {
                        if (actor.getCountry().equals(i[3])) {
                            countryActor.add(actor);
                        }
                    }
                    if (countryActor.isEmpty()) {
                        outputFile.write("No result\n");
                    } else {
                        for (Actor printActor : countryActor) {
                            outputFile.write(printActor.getName() + " " + printActor.getSurname() + " " + printActor.getHeight() + " cm\n");
                        }
                    }
                    outputFile.write("\nChildActors:\n");
                    ArrayList<ChildActor> countryChildActor = new ArrayList<>();
                    for (ChildActor childActor : childActorObjectsList) {
                        if (childActor.getCountry().equals(i[3])) {
                            countryChildActor.add(childActor);
                        }
                    }
                    if (countryChildActor.isEmpty()) {
                        outputFile.write("No result\n");
                    } else {
                        for (ChildActor printChildActor : countryChildActor) {
                            outputFile.write(printChildActor.getName() + " " + printChildActor.getSurname() + " " + printChildActor.getAge() + "\n");
                        }
                    }
                    outputFile.write("\nStuntPerformers:\n");
                    ArrayList<StundPerformer> countryStundPerformer = new ArrayList<>();
                    for (StundPerformer stundPerformer : stundPerformerObjectsList) {
                        if (stundPerformer.getCountry().equals(i[3])) {
                            countryStundPerformer.add(stundPerformer);
                        }
                    }
                    if (countryStundPerformer.isEmpty()) {
                        outputFile.write("No result\n");
                    } else {
                        for (StundPerformer printStundPerformer : countryStundPerformer) {
                            outputFile.write(printStundPerformer.getName() + " " + printStundPerformer.getSurname() + " " + printStundPerformer.getHeight() + " cm\n");
                        }
                    }
                } else if (i[1].equals("FILMS") && i[3].equals("RATE") && i[4].equals("DEGREE")) {
                    outputFile.write("FeatureFilm:\n");
                    if (featureFilmsObjectList.isEmpty()){
                        outputFile.write("\nNo result\n");
                    }else {
                        ArrayList<FeatureFilm> sortedFeatureFilm = new ArrayList<>(featureFilmsObjectList);
                        sortedFeatureFilm.sort(Comparator.comparing(FeatureFilm::AverageRate).reversed());
                        for (FeatureFilm featureFilm : sortedFeatureFilm) {
                            outputFile.write(featureFilm.getFilmTitle() + " (" + featureFilm.getReleaseDate().substring(6) + ") Ratings: " + featureFilm.AverageRateString(featureFilm) + "/10 from " + featureFilm.getRatingList().size() + " users\n");
                        }
                    }
                    outputFile.write("\nShortFilm:\n");
                    if (shortFilmsObjectList.isEmpty()){
                        outputFile.write("\nNo result\n");
                    } else {
                        ArrayList<ShortFilm> sortedShortFilm = new ArrayList<>(shortFilmsObjectList);
                        sortedShortFilm.sort(Comparator.comparing(ShortFilm::AverageRate).reversed());
                        for(ShortFilm shortFilm:sortedShortFilm){
                            outputFile.write(shortFilm.getFilmTitle() + " (" + shortFilm.getReleaseDate().substring(6) + ") Ratings: " + shortFilm.AverageRateString(shortFilm) + "/10 from " + shortFilm.getRatingList().size() + " users\n");
                        }
                    }
                    outputFile.write("\nDocumentary:\n");
                    if(documentaryObjectList.isEmpty()){
                        outputFile.write("\nNo result\n");
                    } else {
                        ArrayList<Documentary> sortedDocumentaryList = new ArrayList<>(documentaryObjectList);
                        sortedDocumentaryList.sort(Comparator.comparing(Documentary::AverageRate).reversed());
                        for (Documentary documentary:sortedDocumentaryList){
                            outputFile.write(documentary.getFilmTitle() + " (" + documentary.getReleaseDate().substring(6) + ") Ratings: " + documentary.AverageRateString(documentary) + "/10 from " + documentary.getRatingList().size() + " users\n");
                        }
                    }
                    outputFile.write("\nTVSeries:\n");
                    if (tvSeriesObjectList.isEmpty()) {
                        outputFile.write("\nNo result\n");
                    } else {
                        ArrayList<TvSeries> sortedTvSeries = new ArrayList<>(tvSeriesObjectList);
                        sortedTvSeries.sort(Comparator.comparing(TvSeries::AverageRate).reversed());
                        for (TvSeries tvSeries:sortedTvSeries){
                            outputFile.write(tvSeries.getFilmTitle() + " (" + tvSeries.getStartDate().substring(6) + "-" + tvSeries.getEndDate().substring(6) + ") Ratings: " + tvSeries.AverageRateString(tvSeries) + "/10 from " + tvSeries.getRatingList().size() + " users\n");
                        }
                    }
                }
            } else if (i[0].equals("EDIT")) {
                User editRateUser = null;
                for (User user : userObjectsList) {
                    if (user.getId().equals(i[2])) {
                        editRateUser = user;
                        break;
                    }
                }
                Film editRateFilm = null;
                for (Film film:allFilmsObjectList){
                    if (film.getFilmID().equals(i[3])){
                        editRateFilm = film;
                    }
                }
                if (editRateUser == null || editRateFilm == null || !editRateUser.getRateDict().containsKey(editRateFilm.getFilmTitle())) {
                    outputFile.write("Command Failed\nUser ID: " + i[2] + "\n" + "Film ID: " + i[3] + "\n");
                } else {
                    int oldRate = editRateUser.getRateDict().get(editRateFilm.getFilmTitle());
                    editRateUser.EditRate(editRateFilm.getFilmTitle(), Integer.parseInt(i[4]));
                    int oldRateIndex = 0;
                    for (int k = 0; k < editRateFilm.getRatingList().size(); k++) {
                        if (editRateFilm.getRatingList().get(k) == oldRate) {
                            oldRateIndex = k;
                            break;
                        }
                    }
                    editRateFilm.EditRate(oldRateIndex, Integer.parseInt(i[4]));
                    outputFile.write("New ratings done successfully\nFilm title: " + editRateFilm.getFilmTitle() + "\nYour rating: " + i[4] + "\n");
                }
            } else if (i[0].equals("REMOVE")) {
                User removeRateUser = null;
                for (User user : userObjectsList) {
                    if (user.getId().equals(i[2])) {
                        removeRateUser = user;
                        break;
                    }
                }
                Film removeRateFilm = null;
                for (Film film:allFilmsObjectList){
                    if (film.getFilmID().equals(i[3])){
                        removeRateFilm = film;
                    }
                }
                if (removeRateUser == null || removeRateFilm == null || !removeRateUser.getRateDict().containsKey(removeRateFilm.getFilmTitle())) {
                    outputFile.write("Command Failed\nUser ID: " + i[2] + "\n" + "Film ID: " + i[3] + "\n");
                } else {
                    int oldRate = removeRateUser.getRateDict().get(removeRateFilm.getFilmTitle());
                    removeRateUser.RemoveRate(removeRateFilm.getFilmTitle());
                    int oldRateIndex = 0;
                    for (int k = 0; k < removeRateFilm.getRatingList().size(); k++) {
                        if (removeRateFilm.getRatingList().get(k) == oldRate) {
                            oldRateIndex = k;
                            break;
                        }
                    }
                    removeRateFilm.RemoveRate(oldRateIndex);
                    outputFile.write("Your film rating was removed successfully\nFilm title: " + removeRateFilm.getFilmTitle() + "\n");
                }
            }
            outputFile.write("\n-----------------------------------------------------------------------------------------------------\n");
        }
        outputFile.close();
    }
}