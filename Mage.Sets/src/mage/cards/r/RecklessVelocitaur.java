package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CrewsVehicleSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecklessVelocitaur extends CardImpl {

    public RecklessVelocitaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever this creature saddles a Mount or crews a Vehicle during your main phase, that Mount or Vehicle gets +2/+0 and gains trample until end of turn.

        Effect boostEffect = new BoostTargetEffect(2, 0, Duration.EndOfTurn)
                .setText("that Mount or Vehicle gets +2/+0");
        Effect abilityGainEffect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains trample until end of turn");
        Ability ability = new CrewsVehicleSourceTriggeredAbility(boostEffect, true, true);
        ability.addEffect(abilityGainEffect);
        this.addAbility(ability);
    }

    private RecklessVelocitaur(final RecklessVelocitaur card) {
        super(card);
    }

    @Override
    public RecklessVelocitaur copy() {
        return new RecklessVelocitaur(this);
    }
}
