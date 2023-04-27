
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class GodheadOfAwe extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Other creatures");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public GodheadOfAwe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W/U}{W/U}{W/U}{W/U}{W/U}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Other creatures have base power and toughness 1/1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SetBasePowerToughnessAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));
    }

    private GodheadOfAwe(final GodheadOfAwe card) {
        super(card);
    }

    @Override
    public GodheadOfAwe copy() {
        return new GodheadOfAwe(this);
    }
}
