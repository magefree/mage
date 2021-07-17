package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PurpleWorm extends CardImpl {

    public PurpleWorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.WORM);
        this.power = new MageInt(8);
        this.toughness = new MageInt(7);

        // This spell costs {2} less to cast if a creature died this turn.
        Ability ability = new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(2, MorbidCondition.instance)
        );
        ability.setRuleAtTheTop(true);
        this.addAbility(ability.addHint(MorbidHint.instance));

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));
    }

    private PurpleWorm(final PurpleWorm card) {
        super(card);
    }

    @Override
    public PurpleWorm copy() {
        return new PurpleWorm(this);
    }
}
