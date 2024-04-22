package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockingOrBlockedBySourcePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattleScarredGoblin extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature blocking it");

    static {
        filter.add(BlockingOrBlockedBySourcePredicate.BLOCKING);
    }

    public BattleScarredGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Battle-Scarred Goblin becomes blocked, it deals 1 damage to each creature blocking it.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(
                new DamageAllEffect(1, "it", filter), false
        ));
    }

    private BattleScarredGoblin(final BattleScarredGoblin card) {
        super(card);
    }

    @Override
    public BattleScarredGoblin copy() {
        return new BattleScarredGoblin(this);
    }
}
