package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MalevolentHermit extends CardImpl {

    public MalevolentHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.b.BenevolentGeist.class;

        // {U}, Sacrifice Malevolent Hermit: Counter target noncreature spell unless its controller pays {3}.
        Ability ability = new SimpleActivatedAbility(
                new CounterUnlessPaysEffect(new GenericManaCost(3)), new ManaCostsImpl<>("{U}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.addAbility(ability);

        // Disturb {2}{U}
        this.addAbility(new TransformAbility());
        this.addAbility(new DisturbAbility(new ManaCostsImpl<>("{2}{U}")));
    }

    private MalevolentHermit(final MalevolentHermit card) {
        super(card);
    }

    @Override
    public MalevolentHermit copy() {
        return new MalevolentHermit(this);
    }
}
