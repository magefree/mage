
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnTargetToOwnersLibraryPermanentEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

/**
 *
 * @author Styxo
 */
public final class SithManipulator extends CardImpl {

    public SithManipulator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Sith Manipulator enters the battlefield, return target creature to its owner's hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect()),
                new InvertCondition(HateCondition.instance),
                "When Sith Manipulator enters the battlefield, return target creature to its owner's hand");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability, new LifeLossOtherFromCombatWatcher());

        // <i>Hate</i> &mdash; If opponent lost life from source other than combat damage this turn, put that card on top of its owner's library instead.
        ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new ReturnTargetToOwnersLibraryPermanentEffect(true)),
                HateCondition.instance,
                "<i>Hate</i> &mdash; If opponent lost life from source other than combat damage this turn, put that card on top of its owner's library instead");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability, new LifeLossOtherFromCombatWatcher());

    }

    private SithManipulator(final SithManipulator card) {
        super(card);
    }

    @Override
    public SithManipulator copy() {
        return new SithManipulator(this);
    }
}
