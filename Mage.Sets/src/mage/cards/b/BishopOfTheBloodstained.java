
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class BishopOfTheBloodstained extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Vampire you control");

    static {
        filter.add(new SubtypePredicate(SubType.VAMPIRE));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public BishopOfTheBloodstained(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Bishop of the Bloodstained enters the battlefield, target opponent loses 1 life for each vampire you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(new PermanentsOnBattlefieldCount(filter)));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public BishopOfTheBloodstained(final BishopOfTheBloodstained card) {
        super(card);
    }

    @Override
    public BishopOfTheBloodstained copy() {
        return new BishopOfTheBloodstained(this);
    }
}
