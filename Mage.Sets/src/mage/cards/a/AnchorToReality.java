package mage.cards.a;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author weirddan455
 */
public final class AnchorToReality extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("an artifact or creature");

    static {
        filter.add(Predicates.or(CardType.ARTIFACT.getPredicate(), CardType.CREATURE.getPredicate()));
    }

    public AnchorToReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}{U}");

        // As an additional cost to cast this spell, sacrifice an artifact or creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(filter));

        // Search your library for an Equipment or Vehicle card, put it onto the battlefield, then shuffle. If its mana value is less than the sacrificed permanent's mana value, scry 2.
        this.getSpellAbility().addEffect(new AnchorToRealityEffect());
    }

    private AnchorToReality(final AnchorToReality card) {
        super(card);
    }

    @Override
    public AnchorToReality copy() {
        return new AnchorToReality(this);
    }
}

class AnchorToRealityEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("an Equipment or Vehicle card");

    static {
        filter.add(Predicates.or(SubType.EQUIPMENT.getPredicate(), SubType.VEHICLE.getPredicate()));
    }

    public AnchorToRealityEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Search your library for an Equipment or Vehicle card, put it onto the battlefield, then shuffle. If its mana value is less than the sacrificed permanent's mana value, scry 2";
    }

    private AnchorToRealityEffect(final AnchorToRealityEffect effect) {
        super(effect);
    }

    @Override
    public AnchorToRealityEffect copy() {
        return new AnchorToRealityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        controller.searchLibrary(target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            controller.shuffleLibrary(source, game);
            return true;
        }
        int searchedManaValue = card.getManaValue();
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        controller.shuffleLibrary(source, game);
        int sacrificedManaValue = 0;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                List<Permanent> sacrificedPermanents = ((SacrificeTargetCost) cost).getPermanents();
                if (sacrificedPermanents.size() > 0) {
                    sacrificedManaValue = sacrificedPermanents.get(0).getManaValue();
                    break;
                }
            }
        }
        if (searchedManaValue < sacrificedManaValue) {
            controller.scry(2, source, game);
        }
        return true;
    }
}
