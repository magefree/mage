package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class CantankerousCaptain extends CardImpl {

    public CantankerousCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.TELLARITE);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, target creature you control gets +1/+0 until end of turn. Then that creature gains menace until end of turn if creatures you control have total power 8 or greater.
        Ability ability = new BeginningOfCombatTriggeredAbility(new BoostTargetEffect(1, 0));
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addEffect(new ConditionalContinuousEffect(
            new GainAbilityTargetEffect(new MenaceAbility()),
            FormidableCondition.instance,
            "Then that creature gains menace until end of turn if creatures you control have total power 8 or greater"
        ));
        this.addAbility(ability);
    }

    private CantankerousCaptain(final CantankerousCaptain card) {
        super(card);
    }

    @Override
    public CantankerousCaptain copy() {
        return new CantankerousCaptain(this);
    }
}
