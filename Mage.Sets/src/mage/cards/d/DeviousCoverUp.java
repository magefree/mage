package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author TheElk801
 */
public final class DeviousCoverUp extends CardImpl {

    public DeviousCoverUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");

        // Counter target spell. If that spell is countered this way, exile it instead of putting it into its owner's graveyard.
        this.getSpellAbility().addEffect(new CounterTargetWithReplacementEffect(Zone.EXILED));
        this.getSpellAbility().addTarget(new TargetSpell());

        // You may shuffle up to four target cards from your graveyard into your library.
        this.getSpellAbility().addEffect(new DeviousCoverUpEffect().setTargetPointer(new SecondTargetPointer()));
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 4));
    }

    private DeviousCoverUp(final DeviousCoverUp card) {
        super(card);
    }

    @Override
    public DeviousCoverUp copy() {
        return new DeviousCoverUp(this);
    }
}

class DeviousCoverUpEffect extends OneShotEffect {

    public DeviousCoverUpEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may shuffle up to four target cards "
                + "from your graveyard into your library.";
    }

    public DeviousCoverUpEffect(final DeviousCoverUpEffect effect) {
        super(effect);
    }

    @Override
    public DeviousCoverUpEffect copy() {
        return new DeviousCoverUpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !controller.chooseUse(outcome, "Shuffle the targeted cards into your library?", source, game)) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID targetId : targetPointer.getTargets(game, source)) {
            Card card = game.getCard(targetId);
            if (card != null) {
                cards.add(card);
            }
        }
        controller.putCardsOnTopOfLibrary(cards, game, source, false);
        controller.shuffleLibrary(source, game);
        return true;
    }
}
