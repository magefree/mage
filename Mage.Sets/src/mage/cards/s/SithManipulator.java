
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
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
        // <i>Hate</i> &mdash; If opponent lost life from source other than combat damage this turn, put that card on top of its owner's library instead.
        Effect effect = new ConditionalOneShotEffect(
                new PutOnLibraryTargetEffect(true),
                new ReturnToHandTargetEffect(),
                HateCondition.instance,
                "return target creature to its owner's hand." +
                        "<br><i>Hate</i> &mdash; If opponent lost life from source other than combat damage this turn, " +
                        "put that card on top of its owner's library instead"
        );
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
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
