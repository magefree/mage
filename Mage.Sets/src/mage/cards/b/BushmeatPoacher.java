package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BushmeatPoacher extends CardImpl {

    public BushmeatPoacher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {1}, {T}, Sacrifice another creature: You gain life equal to that creature's toughness. Draw a card.
        Ability ability = new SimpleActivatedAbility(new BushmeatPoacherEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));
        this.addAbility(ability);
    }

    private BushmeatPoacher(final BushmeatPoacher card) {
        super(card);
    }

    @Override
    public BushmeatPoacher copy() {
        return new BushmeatPoacher(this);
    }
}

class BushmeatPoacherEffect extends OneShotEffect {

    BushmeatPoacherEffect() {
        super(Outcome.Benefit);
        staticText = "you gain life equal to the sacrificed creature's toughness. Draw a card";
    }

    private BushmeatPoacherEffect(final BushmeatPoacherEffect effect) {
        super(effect);
    }

    @Override
    public BushmeatPoacherEffect copy() {
        return new BushmeatPoacherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        SacrificeTargetCost cost = source
                .getCosts()
                .stream()
                .filter(SacrificeTargetCost.class::isInstance)
                .map(SacrificeTargetCost.class::cast)
                .findFirst()
                .orElse(null);
        if (cost == null) {
            return false;
        }
        Permanent permanent = cost.getPermanents().get(0);
        if (permanent == null) {
            return false;
        }
        int amount = permanent.getToughness().getValue();
        if (amount > 0) {
            player.gainLife(amount, game, source);
        }
        return player.drawCards(1, source, game) > 0;
    }
}