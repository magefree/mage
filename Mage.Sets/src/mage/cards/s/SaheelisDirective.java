package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.ImproviseAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author TheElk801
 */
public final class SaheelisDirective extends CardImpl {

    public SaheelisDirective(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}{R}");
        

        // Improvise
        this.addAbility(new ImproviseAbility());

        // Reveal the top X cards of your library. You may put any number of artifact cards with converted mana cost X or less from among them onto the battlefield. Then put all cards revealed this way that weren't put onto the battlefield into your graveyard.
        this.getSpellAbility().addEffect(new SaheelisDirectiveEffect());
    }

    private SaheelisDirective(final SaheelisDirective card) {
        super(card);
    }

    @Override
    public SaheelisDirective copy() {
        return new SaheelisDirective(this);
    }
}
class SaheelisDirectiveEffect extends OneShotEffect {

    public SaheelisDirectiveEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "Reveal the top X cards of your library. "
                + "You may put any number of artifact cards with "
                + "mana value X or less from among them onto the battlefield. "
                + "Then put all cards revealed this way that weren't "
                + "put onto the battlefield into your graveyard.";
    }

    public SaheelisDirectiveEffect(final SaheelisDirectiveEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int xValue = source.getManaCostsToPay().getX();
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, xValue));
        if (!cards.isEmpty()) {
            controller.revealCards(source, cards, game);
            FilterCard filter = new FilterArtifactCard("artifact cards with mana value " + xValue + " or less to put onto the battlefield");
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
            TargetCard target1 = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, filter);
            target1.setNotTarget(true);
            controller.choose(Outcome.PutCardInPlay, cards, target1, source, game);
            Cards toBattlefield = new CardsImpl(target1.getTargets());
            cards.removeAll(toBattlefield);
            controller.moveCards(toBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, false, null);
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        }
        return true;
    }

    @Override
    public SaheelisDirectiveEffect copy() {
        return new SaheelisDirectiveEffect(this);
    }

}
