package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author TheElk801
 */
public final class GoblinLocksmith extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creatures with defender");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public GoblinLocksmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Goblin Locksmith attacks, creatures with defender can't block this turn.
        this.addAbility(new AttacksTriggeredAbility(
                new CantBlockAllEffect(filter, Duration.EndOfTurn), false
        ));
    }

    private GoblinLocksmith(final GoblinLocksmith card) {
        super(card);
    }

    @Override
    public GoblinLocksmith copy() {
        return new GoblinLocksmith(this);
    }
}
