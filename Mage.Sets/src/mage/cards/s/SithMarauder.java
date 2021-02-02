
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

/**
 *
 * @author Styxo
 */
public final class SithMarauder extends CardImpl {

    public SithMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // <i>Hate</i> &mdash; When Sith Marauder enters the battlefield, if an opponent lost life from a source other than combat damage this turn, Sith Marauder deals 3 damage to any target.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3)),
                HateCondition.instance,
                "<i>Hate</i> &mdash; When {this} enters the battlefield, if an opponent lost life from a source other than combat damage this turn, {this} deals 3 damage to any target");
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability, new LifeLossOtherFromCombatWatcher());
    }

    private SithMarauder(final SithMarauder card) {
        super(card);
    }

    @Override
    public SithMarauder copy() {
        return new SithMarauder(this);
    }
}
