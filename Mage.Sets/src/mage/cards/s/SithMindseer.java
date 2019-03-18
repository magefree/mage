
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

/**
 *
 * @author Styxo
 */
public final class SithMindseer extends CardImpl {

    public SithMindseer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // <i>Hate</i> &mdash; When Sith Mindseer enters the battlefield, if an opponent loses life from a source other than combat damage, gain control of target creature for as long as Sith Mindseer remains on the battlefield.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.WhileOnBattlefield)),
                HateCondition.instance,
                "<i>Hate</i> &mdash; When Sith Mindseer enters the battlefield, if an opponent loses life from a source other than combat damage, gain control of target creature for as long as Sith Mindseer remains on the battlefield.");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability, new LifeLossOtherFromCombatWatcher());
    }

    public SithMindseer(final SithMindseer card) {
        super(card);
    }

    @Override
    public SithMindseer copy() {
        return new SithMindseer(this);
    }
}
