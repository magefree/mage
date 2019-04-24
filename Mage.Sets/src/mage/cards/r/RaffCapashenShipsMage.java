
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CastAsThoughItHadFlashAllEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.HistoricPredicate;

/**
 *
 * @author TheElk801
 */
public final class RaffCapashenShipsMage extends CardImpl {

    private static final FilterCard filter = new FilterCard("historic spells");

    static {
        filter.add(new HistoricPredicate());
    }

    public RaffCapashenShipsMage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may cast historic spells as though they had flash.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new CastAsThoughItHadFlashAllEffect(Duration.WhileOnBattlefield, filter, false)
                        .setText("You may cast historic spells as though they had flash. <i>(Artifacts, legendaries, and Sagas are historic.)</i>")));
    }

    public RaffCapashenShipsMage(final RaffCapashenShipsMage card) {
        super(card);
    }

    @Override
    public RaffCapashenShipsMage copy() {
        return new RaffCapashenShipsMage(this);
    }
}
