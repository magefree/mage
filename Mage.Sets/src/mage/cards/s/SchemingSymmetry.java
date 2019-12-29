package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SchemingSymmetry extends CardImpl {

    public SchemingSymmetry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Choose two target players. Each of them searches their library for a card, then shuffles their library and puts that card on top of it.
        this.getSpellAbility().addEffect(new SchemingSymmetryEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(2));
    }

    private SchemingSymmetry(final SchemingSymmetry card) {
        super(card);
    }

    @Override
    public SchemingSymmetry copy() {
        return new SchemingSymmetry(this);
    }
}

class SchemingSymmetryEffect extends OneShotEffect {

    SchemingSymmetryEffect() {
        super(Outcome.Benefit);
        staticText = "Choose two target players. Each of them searches their library for a card, " +
                "then shuffles their library and puts that card on top of it.";
    }

    private SchemingSymmetryEffect(final SchemingSymmetryEffect effect) {
        super(effect);
    }

    @Override
    public SchemingSymmetryEffect copy() {
        return new SchemingSymmetryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        source.getTargets()
                .get(0)
                .getTargets()
                .stream()
                .map(playerId -> game.getPlayer(playerId))
                .filter(player -> player != null)
                .forEach(player -> {
                    TargetCardInLibrary targetCard = new TargetCardInLibrary();
                    if (player.searchLibrary(targetCard, source, game)) {
                        Cards cards = new CardsImpl();
                        cards.add(targetCard.getFirstTarget());
                        player.shuffleLibrary(source, game);
                        player.putCardsOnTopOfLibrary(cards, game, source, false);
                    }
                });
        return true;
    }
}