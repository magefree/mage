package mage.game;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.command.emblems.MentalMagicEmblem;
import mage.game.match.MatchType;
import mage.game.mulligan.Mulligan;
import mage.game.turn.TurnMod;
import mage.watchers.common.MentalMagicNamesWatcher;

import java.util.UUID;

/**
 * Mental Magic (https://mtg.fandom.com/wiki/Mental_Magic): normal rules, plus every player gets the Mental Magic emblem.
 * Any card can be played face down as a land that taps for any color, or turned into another card with the same
 * mana cost. Each card name can be played only once per game.
 *
 * @author xico7
 */
public class MentalMagicGame extends GameImpl {

    private final boolean freeForAll;
    private final boolean sameType;

    /**
     * @param freeForAll two player duel or free for all
     * @param sameType   new cards must share a card type with the card in hand
     */
    public MentalMagicGame(MultiplayerAttackOption attackOption, RangeOfInfluence range, Mulligan mulligan,
                           int minimumDeckSize, int startLife, int startHandSize,
                           boolean freeForAll, boolean sameType) {
        super(attackOption, range, mulligan, minimumDeckSize, startLife, startHandSize);
        this.freeForAll = freeForAll;
        this.sameType = sameType;
    }

    protected MentalMagicGame(final MentalMagicGame game) {
        super(game);
        this.freeForAll = game.freeForAll;
        this.sameType = game.sameType;
    }

    @Override
    public MatchType getGameType() {
        if (freeForAll) {
            return sameType ? new MentalMagicSameTypeFreeForAllType() : new MentalMagicFreeForAllType();
        }
        return sameType ? new MentalMagicSameTypeDuelType() : new MentalMagicDuelType();
    }

    @Override
    public int getNumPlayers() {
        return freeForAll ? getPlayers().size() : 2;
    }

    @Override
    public void initGameDefaultWatchers() {
        super.initGameDefaultWatchers();
        getState().addWatcher(new MentalMagicNamesWatcher());
    }

    @Override
    protected void init(UUID choosingPlayerId) {
        for (UUID playerId : state.getPlayerList(startingPlayerId)) {
            if (getPlayer(playerId) != null) {
                addEmblem(new MentalMagicEmblem(sameType), null, playerId);
            }
        }
        super.init(choosingPlayerId);
        if (!freeForAll) {
            state.getTurnMods().add(new TurnMod(startingPlayerId).withSkipStep(PhaseStep.DRAW));
        }
    }

    @Override
    public MentalMagicGame copy() {
        return new MentalMagicGame(this);
    }

}
