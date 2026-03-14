package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MalevolentHermit extends TransformingDoubleFacedCard {

    public MalevolentHermit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WIZARD}, "{1}{U}",
                "Benevolent Geist",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIRIT, SubType.WIZARD}, "U"
        );

        // Malevolent Hermit
        this.getLeftHalfCard().setPT(2, 1);

        // {U}, Sacrifice Malevolent Hermit: Counter target noncreature spell unless its controller pays {3}.
        Ability ability = new SimpleActivatedAbility(
                new CounterUnlessPaysEffect(new GenericManaCost(3)), new ManaCostsImpl<>("{U}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        this.getLeftHalfCard().addAbility(ability);

        // Disturb {2}{U}
        this.getLeftHalfCard().addAbility(new DisturbAbility(this, "{2}{U}"));

        // Benevolent Geist
        this.getRightHalfCard().setPT(2, 2);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // Noncreature spells you control can't be countered.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new CantBeCounteredControlledEffect(
                StaticFilters.FILTER_SPELLS_NON_CREATURE, Duration.WhileOnBattlefield
        )));

        // If Benevolent Geist would be put into a graveyard from anywhere, exile it instead.
        this.getRightHalfCard().addAbility(DisturbAbility.makeBackAbility());
    }

    private MalevolentHermit(final MalevolentHermit card) {
        super(card);
    }

    @Override
    public MalevolentHermit copy() {
        return new MalevolentHermit(this);
    }
}
