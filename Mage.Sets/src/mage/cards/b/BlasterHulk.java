package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.EnergySpentOrLostThisTurnCount;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 *
 * @author grimreap124
 */
public final class BlasterHulk extends CardImpl {

    public BlasterHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}{R}{R}");
        
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // This spell costs {1} less to cast for each {E} you've paid or lost this turn.
        DynamicValue xValue = EnergySpentOrLostThisTurnCount.instance;
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SpellCostReductionForEachSourceEffect(1, xValue))
                .addHint(new ValueHint("{E} you've paid or lost this turn", xValue))
        );
        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Blaster Hulk attacks, you get {E}{E}, then you may pay eight {E}. When you do, Blaster Hulk deals 8 damage divided as you choose among up to eight targets.
        Ability ability = new AttacksTriggeredAbility(new GetEnergyCountersControllerEffect(2));
        ReflexiveTriggeredAbility reflexiveAbility = new ReflexiveTriggeredAbility(new DamageMultiEffect(8)
                .setText("{this} deals 8 damage divided as you choose among up to eight targets"), false);
        reflexiveAbility.addTarget(new TargetAnyTargetAmount(8));
        ability.addEffect(new DoWhenCostPaid(reflexiveAbility, new PayEnergyCost(8),
                "Pay eight {E} to deal 8 damage divided as you choose among up to eight targets?"
        ).concatBy(", then"));

        this.addAbility(ability);
    }

    private BlasterHulk(final BlasterHulk card) {
        super(card);
    }

    @Override
    public BlasterHulk copy() {
        return new BlasterHulk(this);
    }
}
