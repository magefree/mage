package mage.game;

import mage.game.match.MatchImpl;
import mage.game.match.MatchOptions;
import mage.game.mulligan.Mulligan;

/**
 * Base match of all Mental Magic table types, subclasses choose the variant
 *
 * @author xico7
 */
public abstract class MentalMagicMatch extends MatchImpl {

    private final boolean freeForAll;
    private final boolean sameType;

    protected MentalMagicMatch(MatchOptions options, boolean freeForAll, boolean sameType) {
        super(options);
        this.freeForAll = freeForAll;
        this.sameType = sameType;
    }

    @Override
    public void startGame() throws GameException {
        Mulligan mulligan = options.getMulliganType().getMulligan(options.getFreeMulligans());
        boolean isLimitedDeck = options.isLimited() || "Limited".equals(options.getDeckType());
        int startLife = options.isCustomStartLifeEnabled() ? options.getCustomStartLife() : 20;
        int startHandSize = options.isCustomStartHandSizeEnabled() ? options.getCustomStartHandSize() : 7;
        MentalMagicGame game = new MentalMagicGame(
                options.getAttackOption(), options.getRange(), mulligan,
                isLimitedDeck ? 40 : 60, startLife, startHandSize,
                freeForAll, sameType
        );
        game.setStartMessage(this.createGameStartMessage());
        initGame(game);
        games.add(game);
    }

}
