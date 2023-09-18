

package mage.server.tournament;

import mage.cards.Sets;
import mage.game.draft.DraftCube;
import mage.game.tournament.Tournament;
import mage.game.tournament.TournamentOptions;
import mage.game.tournament.TournamentType;
import mage.server.draft.CubeFactory;
import mage.view.TournamentTypeView;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public enum TournamentFactory {
    instance;
    private static final Logger logger = Logger.getLogger(TournamentFactory.class);

    private final Map<String, Class<Tournament>> tournaments = new HashMap<>();
    private final Map<String, TournamentType> tournamentTypes = new HashMap<>();
    private final List<TournamentTypeView> tournamentTypeViews = new ArrayList<>();



    public Tournament createTournament(String tournamentType, TournamentOptions options) {

        Tournament tournament;
        try {
            Constructor<Tournament> con = tournaments.get(tournamentType).getConstructor(TournamentOptions.class);
            tournament = con.newInstance(options);
            // transfer set information, create short info string for included sets
            tournament.setTournamentType(tournamentTypes.get(tournamentType));
            if (tournament.getTournamentType().isLimited()) {
                Map<String,Integer> setInfo = new LinkedHashMap<>();
                for (String setCode: options.getLimitedOptions().getSetCodes()) {
                    tournament.getSets().add(Sets.findSet(setCode));
                    int count = setInfo.getOrDefault(setCode, 0);
                    setInfo.put(setCode, count + 1);
                }
                tournament.getOptions().getLimitedOptions().setNumberBoosters(tournament.getTournamentType().getNumBoosters());
                if (tournament.getTournamentType().isCubeBooster()) {
                    DraftCube draftCube;

                    if (tournament.getOptions().getLimitedOptions().getCubeFromDeck() != null) {
                        draftCube = CubeFactory.instance.createDeckDraftCube(tournament.getOptions().getLimitedOptions().getDraftCubeName(), tournament.getOptions().getLimitedOptions().getCubeFromDeck());
                    } else {
                        draftCube = CubeFactory.instance.createDraftCube(tournament.getOptions().getLimitedOptions().getDraftCubeName());
                    }
                    tournament.getOptions().getLimitedOptions().setDraftCube(draftCube);
                    tournament.setBoosterInfo(tournament.getOptions().getLimitedOptions().getDraftCubeName());
                } else if (tournament.getTournamentType().isRandom()) {
                    StringBuilder rv = new StringBuilder( "Chaos Draft using sets: ");
                    for (Map.Entry<String, Integer> entry: setInfo.entrySet()){
                        rv.append(entry.getKey());
                        rv.append(';');
                    }
                    tournament.setBoosterInfo(rv.toString());
                } else if (tournament.getTournamentType().isRemixed()) {
                    StringBuilder rv = new StringBuilder( "Chaos Remixed Draft using sets: ");
                    for (Map.Entry<String, Integer> entry: setInfo.entrySet()){
                        rv.append(entry.getKey());
                        rv.append(';');
                    }
                    tournament.setBoosterInfo(rv.toString());
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Map.Entry<String,Integer> entry:setInfo.entrySet()) {
                        sb.append(entry.getValue().toString()).append('x').append(entry.getKey()).append(' ');
                    }
                    tournament.setBoosterInfo(sb.toString());
                }
            } else {
                tournament.setBoosterInfo("");
            }

        } catch (Exception ex) {
            logger.fatal("TournamentFactory error ", ex);
            return null;
        }
        logger.debug("Tournament created: " + tournamentType + ' ' + tournament.getId());

        return tournament;
    }

    public List<TournamentTypeView> getTournamentTypes() {
        return tournamentTypeViews;
    }


    public void addTournamentType(String name, TournamentType tournamentType, Class tournament) {
        if (tournament != null) {
            this.tournaments.put(name, tournament);
            this.tournamentTypes.put(name, tournamentType);
            this.tournamentTypeViews.add(new TournamentTypeView(tournamentType));
        }
    }

}
