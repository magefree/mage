
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class UbulSarGatekeepers extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();
    private static final FilterCreaturePermanent targetFilter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new SubtypePredicate(SubType.GATE));
        targetFilter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public UbulSarGatekeepers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Ubul Sar Gatekeepers enters the battlefield, if you control two or more Gates, target creature an opponent controls gets -2/-2 until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-2, -2, Duration.EndOfTurn)),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1),
                "Whenever {this} enters the battlefield, if you control two or more Gates, target creature an opponent controls gets -2/-2 until end of turn.");
        Target target = new TargetCreaturePermanent(targetFilter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public UbulSarGatekeepers(final UbulSarGatekeepers card) {
        super(card);
    }

    @Override
    public UbulSarGatekeepers copy() {
        return new UbulSarGatekeepers(this);
    }

}
