
package mage.cards.v;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author fireshoes
 */
public final class VesselOfNascency extends CardImpl {

    public VesselOfNascency(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // {1}{G}, Sacrifice Vessel of Nascency: Reveal the top four cards of your library. You may put an artifact, creature, enchantment, land, or
        // planeswalker card from among them into your hand. Put the rest into your graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VesselOfNascencyEffect(), new ManaCostsImpl("{1}{G}"));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private VesselOfNascency(final VesselOfNascency card) {
        super(card);
    }

    @Override
    public VesselOfNascency copy() {
        return new VesselOfNascency(this);
    }
}

class VesselOfNascencyEffect extends OneShotEffect {

    private static final FilterCard filterPutInHand = new FilterCard("an artifact, creature, enchantment, land, or planeswalker card");

    static {
        filterPutInHand.add(Predicates.or(CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                CardType.LAND.getPredicate(),
                CardType.PLANESWALKER.getPredicate()));
    }

    public VesselOfNascencyEffect() {
        super(Outcome.DrawCard);
        this.staticText = "reveal the top four cards of your library. You may put artifact, creature, enchantment, land, or planeswalker card from among "
                + "them into your hand. Put the rest into your graveyard";
    }

    public VesselOfNascencyEffect(final VesselOfNascencyEffect effect) {
        super(effect);
    }

    @Override
    public VesselOfNascencyEffect copy() {
        return new VesselOfNascencyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
            boolean properCardFound = cards.count(filterPutInHand, source.getSourceId(), source, game) > 0;
            if (!cards.isEmpty()) {
                controller.revealCards(sourceObject.getName(), cards, game);
                TargetCard target = new TargetCard(Zone.LIBRARY, filterPutInHand);
                if (properCardFound
                        && controller.chooseUse(outcome, "Put an artifact, creature, enchantment, land, or planeswalker card into your hand?", source, game)
                        && controller.choose(Outcome.DrawCard, cards, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        cards.remove(card);
                        controller.moveCards(card, Zone.HAND, source, game);
                    }

                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            return true;
        }
        return false;
    }
}
