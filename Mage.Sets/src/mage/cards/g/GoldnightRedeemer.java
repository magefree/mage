
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author noxx

 */
public final class GoldnightRedeemer extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("other creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GoldnightRedeemer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        // When Goldnight Redeemer enters the battlefield, you gain 2 life for each other creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(new PermanentsOnBattlefieldCount(filter, 2)), false));
    }

    private GoldnightRedeemer(final GoldnightRedeemer card) {
        super(card);
    }

    @Override
    public GoldnightRedeemer copy() {
        return new GoldnightRedeemer(this);
    }
}
