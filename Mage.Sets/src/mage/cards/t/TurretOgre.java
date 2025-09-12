package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.PowerPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TurretOgre extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("you control another creature with power 4 or greater");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);

    public TurretOgre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Turret Ogre enters the battlefield, if you control another creature with power 4 or greater, Turret Ogre deals 2 damage to each opponent.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamagePlayersEffect(2, TargetController.OPPONENT)).withInterveningIf(condition));
    }

    private TurretOgre(final TurretOgre card) {
        super(card);
    }

    @Override
    public TurretOgre copy() {
        return new TurretOgre(this);
    }
}
