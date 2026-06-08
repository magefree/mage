package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HYDRAAssaultRobot extends CardImpl {

    private static final FilterControlledPermanent filter
        = new FilterControlledPermanent("another Villain and/or artifact you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
            CardType.ARTIFACT.getPredicate(),
            SubType.VILLAIN.getPredicate()
        ));
    }

    public HYDRAAssaultRobot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever another Villain and/or artifact you control enters, this creature deals 1 damage to target opponent.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
            new DamagePlayersEffect(1, TargetController.OPPONENT),
            filter
        ));

    }

    private HYDRAAssaultRobot(final HYDRAAssaultRobot card) {
        super(card);
    }

    @Override
    public HYDRAAssaultRobot copy() {
        return new HYDRAAssaultRobot(this);
    }
}
