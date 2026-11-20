package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author muz
 */
public final class CoOptionAdjutant extends CardImpl {

    public CoOptionAdjutant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Artifact creatures you control have a combat damage triggered ability.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1)),
            Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        )));
    }

    private CoOptionAdjutant(final CoOptionAdjutant card) {
        super(card);
    }

    @Override
    public CoOptionAdjutant copy() {
        return new CoOptionAdjutant(this);
    }
}
