

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.PlantToken;

/**
 *
 * @author Loki, nantuko, North
 */
public final class AvengerOfZendikar extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("Plant creature you control");
    private static final FilterControlledPermanent filterLand = new FilterControlledLandPermanent();

    static {
        filter.add(SubType.PLANT.getPredicate());
    }

    public AvengerOfZendikar (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Avenger of Zendikar enters the battlefield, create a 0/1 green Plant creature token for each land you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new PlantToken(), new PermanentsOnBattlefieldCount(filterLand)), false));

        // Landfall - Whenever a land enters the battlefield under your control, you may put a +1/+1 counter on each Plant creature you control.
        this.addAbility(new LandfallAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), true));
    }

    private AvengerOfZendikar(final AvengerOfZendikar card) {
        super(card);
    }

    @Override
    public AvengerOfZendikar copy() {
        return new AvengerOfZendikar(this);
    }
}
