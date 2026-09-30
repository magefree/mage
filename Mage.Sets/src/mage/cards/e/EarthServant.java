

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com, North
 */
public final class EarthServant extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountain you control");

    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }

    public EarthServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(StaticValue.get(0),
                new PermanentsOnBattlefieldCount(filter, 1),
                Duration.WhileOnBattlefield)));
    }

    private EarthServant(final EarthServant card) {
        super(card);
    }

    @Override
    public EarthServant copy() {
        return new EarthServant(this);
    }

}
