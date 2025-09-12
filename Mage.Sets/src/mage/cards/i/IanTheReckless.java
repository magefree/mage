package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.SourceModifiedCondition;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class IanTheReckless extends CardImpl {

    public IanTheReckless(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Ian the Reckless attacks, if it's modified, you may have it deal damage equal to its power to you and any target.
        Ability ability = new AttacksTriggeredAbility(
                new DamageControllerEffect(SourcePermanentPowerValue.NOT_NEGATIVE)
                        .setText("have it deal damage equal to its power to you"), true
        ).withInterveningIf(SourceModifiedCondition.instance);
        ability.addEffect(new DamageTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE).setText("and any target"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private IanTheReckless(final IanTheReckless card) {
        super(card);
    }

    @Override
    public IanTheReckless copy() {
        return new IanTheReckless(this);
    }
}
