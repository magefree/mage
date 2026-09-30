package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class HiveMindCoprocessor extends CardImpl {

    public HiveMindCoprocessor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.BORG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Artifact creatures you control have "Whenever this creature deals combat damage to a player, you may pay 1 life. If you do, draw a card."
        Ability gainedAbility = new DealsCombatDamageToAPlayerTriggeredAbility(
            new DoIfCostPaid(new DrawCardSourceControllerEffect(1),  new PayLifeCost(1))
        );
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
            gainedAbility, Duration.WhileOnBattlefield,
            StaticFilters.FILTER_PERMANENTS_ARTIFACT_CREATURE
        ).setText("Artifact creatures you control have "
            + "\"Whenever this creature deals combat damage to a player, "
            + "you may pay 1 life. If you do, draw a card.\"")
        ));
    }

    private HiveMindCoprocessor(final HiveMindCoprocessor card) {
        super(card);
    }

    @Override
    public HiveMindCoprocessor copy() {
        return new HiveMindCoprocessor(this);
    }
}
