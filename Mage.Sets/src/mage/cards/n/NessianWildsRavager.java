
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.TributeNotPaidCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.TributeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class NessianWildsRavager extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");
    static {
        filter.add(AnotherPredicate.instance);
    }

    public NessianWildsRavager(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.HYDRA);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Tribute 6
        this.addAbility(new TributeAbility(6));
        // When Nessian Wilds Ravager enters the battlefield, if tribute wasn't paid, you may have Nessian Wilds Ravager fight another target creature.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect(), true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability,
                TributeNotPaidCondition.instance,
                "When {this} enters the battlefield, if tribute wasn't paid, " +
                        "you may have {this} fight another target creature. " +
                        "<i>(Each deals damage equal to its power to the other.)</i>"));
    }

    private NessianWildsRavager(final NessianWildsRavager card) {
        super(card);
    }

    @Override
    public NessianWildsRavager copy() {
        return new NessianWildsRavager(this);
    }
}
