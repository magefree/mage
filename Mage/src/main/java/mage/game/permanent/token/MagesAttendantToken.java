package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

/**
 * @author TheElk801
 */
public final class MagesAttendantToken extends TokenImpl {

    public MagesAttendantToken() {
        super("Wizard Token", "1/1 blue Wizard creature token with \"{1}, Sacrifice this creature: Counter target noncreature spell unless its controller pays {1}.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.WIZARD);
        color.setBlue(true);
        power = new MageInt(1);
        toughness = new MageInt(1);

        Ability ability = new SimpleActivatedAbility(
                new CounterUnlessPaysEffect(new GenericManaCost(1)), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost().setText("sacrifice this creature"));
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.addAbility(ability);
    }

    private MagesAttendantToken(final MagesAttendantToken token) {
        super(token);
    }

    public MagesAttendantToken copy() {
        return new MagesAttendantToken(this);
    }
}
