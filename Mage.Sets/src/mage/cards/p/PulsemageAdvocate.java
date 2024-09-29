
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author emerald000
 */
public final class PulsemageAdvocate extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards from an opponent's graveyard");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public PulsemageAdvocate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {tap}: Return three target cards from an opponent's graveyard to their hand. Return target creature card from your graveyard to the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PulsemageAdvocateEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInASingleGraveyard(3, 3, filter));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private PulsemageAdvocate(final PulsemageAdvocate card) {
        super(card);
    }

    @Override
    public PulsemageAdvocate copy() {
        return new PulsemageAdvocate(this);
    }
}

class PulsemageAdvocateEffect extends OneShotEffect {

    PulsemageAdvocateEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return three target cards from an opponent's graveyard to their hand. Return target creature card from your graveyard to the battlefield";
    }

    private PulsemageAdvocateEffect(final PulsemageAdvocateEffect effect) {
        super(effect);
    }

    @Override
    public PulsemageAdvocateEffect copy() {
        return new PulsemageAdvocateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl();
            for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    cards.add(card);
                }
            }
            controller.moveCards(cards, Zone.HAND, source, game);
            Card card = controller.getGraveyard().get(source.getTargets().get(1).getFirstTarget(), game);
            if (card != null) {
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            return true;
        }
        return false;
    }
}
