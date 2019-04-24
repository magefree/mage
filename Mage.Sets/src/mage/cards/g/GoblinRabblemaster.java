
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.AttacksIfAbleAllEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.GoblinToken;
import mage.watchers.common.AttackedThisTurnWatcher;

/**
 *
 * @author Quercitron
 */
public final class GoblinRabblemaster extends CardImpl {

    private static final FilterCreaturePermanent otherGoblinFilter = new FilterCreaturePermanent(SubType.GOBLIN, "Other Goblin creatures you control");
    private static final FilterCreaturePermanent attackingFilter = new FilterCreaturePermanent(SubType.GOBLIN, "other attacking Goblin");

    static {
        otherGoblinFilter.add(new AnotherPredicate());
        otherGoblinFilter.add(new ControllerPredicate(TargetController.YOU));

        attackingFilter.add(new AttackingPredicate());
        attackingFilter.add(new AnotherPredicate());
    }

    public GoblinRabblemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Goblin creatures you control attack each turn if able.
        Effect effect = new AttacksIfAbleAllEffect(otherGoblinFilter);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect), new AttackedThisTurnWatcher());

        // At the beginning of combat on your turn, create a 1/1 red Goblin creature token with haste.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new CreateTokenEffect(new GoblinToken(true)), TargetController.YOU, false));

        // When Goblin Rabblemaster attacks, it gets +1/+0 until end of turn for each other attacking Goblin.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(new PermanentsOnBattlefieldCount(attackingFilter), new StaticValue(0), Duration.EndOfTurn, true), false));
    }

    public GoblinRabblemaster(final GoblinRabblemaster card) {
        super(card);
    }

    @Override
    public GoblinRabblemaster copy() {
        return new GoblinRabblemaster(this);
    }
}
