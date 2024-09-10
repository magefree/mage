package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SalvationColossus extends CardImpl {

    public SalvationColossus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}{W}{W}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever you attack, other creatures you control get +2/+2 and gain indestructible until end of turn.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new BoostControlledEffect(
                2, 2, Duration.EndOfTurn, true
        ).setText("other creatures you control get +2/+2"), 1);
        ability.addEffect(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("and gain indestructible until end of turn"));
        this.addAbility(ability);

        // Unearth--Pay eight {E}.
        this.addAbility(new UnearthAbility(new PayEnergyCost(8)));
    }

    private SalvationColossus(final SalvationColossus card) {
        super(card);
    }

    @Override
    public SalvationColossus copy() {
        return new SalvationColossus(this);
    }
}
