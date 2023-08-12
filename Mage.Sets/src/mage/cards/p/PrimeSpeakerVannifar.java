package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801 and Loki
 */
public final class PrimeSpeakerVannifar extends CardImpl {

    public PrimeSpeakerVannifar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.OOZE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {T}, Sacrifice another creature: Search your library for a creature card with converted mana cost equal to 1 plus the sacrificed creature's converted mana cost, put that card onto the battlefield, then shuffle your library. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD, new PrimeSpeakerVannifarEffect(), new TapSourceCost()
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(
                StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE
        )));
        this.addAbility(ability);
    }

    private PrimeSpeakerVannifar(final PrimeSpeakerVannifar card) {
        super(card);
    }

    @Override
    public PrimeSpeakerVannifar copy() {
        return new PrimeSpeakerVannifar(this);
    }
}

class PrimeSpeakerVannifarEffect extends OneShotEffect {

    PrimeSpeakerVannifarEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for a creature card with mana value equal to 1 " +
                "plus the sacrificed creature's mana value, put that card " +
                "onto the battlefield, then shuffle";
    }

    private PrimeSpeakerVannifarEffect(final PrimeSpeakerVannifarEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sacrificedPermanent = null;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                if (!sacrificeCost.getPermanents().isEmpty()) {
                    sacrificedPermanent = sacrificeCost.getPermanents().get(0);
                }
                break;
            }
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (sacrificedPermanent == null || controller == null) {
            return false;
        }
        int newConvertedCost = sacrificedPermanent.getManaValue() + 1;
        FilterCard filter = new FilterCard("creature card with mana value " + newConvertedCost);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, newConvertedCost));
        filter.add(CardType.CREATURE.getPredicate());
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public PrimeSpeakerVannifarEffect copy() {
        return new PrimeSpeakerVannifarEffect(this);
    }
}
