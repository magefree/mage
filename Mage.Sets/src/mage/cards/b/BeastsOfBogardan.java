
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 *
 * @author nigelzor
 */
public final class BeastsOfBogardan extends CardImpl {

    private static final FilterPermanent controlFilter = new FilterPermanent("nontoken white permanent");

    static {
        controlFilter.add(new ColorPredicate(ObjectColor.WHITE));
        controlFilter.add(TokenPredicate.FALSE);
    }

    public BeastsOfBogardan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));
        // Beasts of Bogardan gets +1/+1 as long as an opponent controls a nontoken white permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostSourceEffect(1, 1, Duration.WhileOnBattlefield),
                new OpponentControlsPermanentCondition(controlFilter),
                "{this} gets +1/+1 as long as an opponent controls a nontoken white permanent")));
    }

    private BeastsOfBogardan(final BeastsOfBogardan card) {
        super(card);
    }

    @Override
    public BeastsOfBogardan copy() {
        return new BeastsOfBogardan(this);
    }
}
