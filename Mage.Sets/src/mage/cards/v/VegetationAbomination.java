package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VegetationAbomination extends CardImpl {

    public VegetationAbomination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.FOOD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {T}, Sacrifice Vegetation Abomination: Roll a six-sided die. You gain life equal to the result.
        Ability ability = new SimpleActivatedAbility(new VegetationAbominationEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private VegetationAbomination(final VegetationAbomination card) {
        super(card);
    }

    @Override
    public VegetationAbomination copy() {
        return new VegetationAbomination(this);
    }
}

class VegetationAbominationEffect extends OneShotEffect {

    VegetationAbominationEffect() {
        super(Outcome.Benefit);
        staticText = "roll a six-sided die. You gain life equal to the result";
    }

    private VegetationAbominationEffect(final VegetationAbominationEffect effect) {
        super(effect);
    }

    @Override
    public VegetationAbominationEffect copy() {
        return new VegetationAbominationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int amount = player.rollDice(outcome, source, game, 6);
        if (amount > 0) {
            player.gainLife(amount, game, source);
        }
        return true;
    }
}
