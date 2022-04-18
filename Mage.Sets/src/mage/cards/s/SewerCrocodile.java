package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.DifferentManaValuesInGraveCondition;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.hint.common.DifferentManaValuesInGraveHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SewerCrocodile extends CardImpl {

    public SewerCrocodile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.CROCODILE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // {3}{U}: Sewer Crocodile can't be blocked this turn. This ability costs {3} less to activate if there are five or more mana values among cards in your graveyard.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{3}{U}")
        );
        ability.addEffect(new InfoEffect("This ability costs {3} less to activate " +
                "if there are five or more mana values among cards in your graveyard"));
        ability.setCostAdjuster(SewerCrocodileAdjuster.instance);
        this.addAbility(ability.addHint(DifferentManaValuesInGraveHint.instance));
    }

    private SewerCrocodile(final SewerCrocodile card) {
        super(card);
    }

    @Override
    public SewerCrocodile copy() {
        return new SewerCrocodile(this);
    }
}

enum SewerCrocodileAdjuster implements CostAdjuster {
    instance;

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (DifferentManaValuesInGraveCondition.FIVE.apply(game, ability)) {
            CardUtil.reduceCost(ability, 3);
        }
    }
}
