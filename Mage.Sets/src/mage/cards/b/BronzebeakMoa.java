

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */


public final class BronzebeakMoa extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another creature under your control");
    static {
        filter.add(AnotherPredicate.instance);
    }
    public BronzebeakMoa (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{W}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever another creature enters the battlefield under your control, Bronzebeak Moa gets +3/+3 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3,3, Duration.EndOfTurn),filter,false));

    }

    public BronzebeakMoa (final BronzebeakMoa card) {
        super(card);
    }

    @Override
    public BronzebeakMoa copy() {
        return new BronzebeakMoa(this);
    }

}
