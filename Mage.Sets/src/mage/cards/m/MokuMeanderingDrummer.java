package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MokuMeanderingDrummer extends CardImpl {

    public MokuMeanderingDrummer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BARD);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a noncreature spell, you may pay {1}. If you do, Moku gets +2/+1 and creatures you control gain haste until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DoIfCostPaid(
                new BoostSourceEffect(2, 1, Duration.EndOfTurn)
                        .setText("{this} gets +2/+1"), new GenericManaCost(1)
        ).addEffect(new GainAbilityAllEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ).concatBy("and")), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false));
    }

    private MokuMeanderingDrummer(final MokuMeanderingDrummer card) {
        super(card);
    }

    @Override
    public MokuMeanderingDrummer copy() {
        return new MokuMeanderingDrummer(this);
    }
}
