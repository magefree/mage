
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.GoblinToken;

/**
 *
 * @author North
 */
public final class KrenkoMobBoss extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("the number of Goblins you control");

    static {
        filter.add(new SubtypePredicate(SubType.GOBLIN));
    }

    public KrenkoMobBoss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {tap}: create X 1/1 red Goblin creature tokens, where X is the number of Goblins you control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new CreateTokenEffect(new GoblinToken(), new PermanentsOnBattlefieldCount(filter)),
                new TapSourceCost()));
    }

    public KrenkoMobBoss(final KrenkoMobBoss card) {
        super(card);
    }

    @Override
    public KrenkoMobBoss copy() {
        return new KrenkoMobBoss(this);
    }
}
