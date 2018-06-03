
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.GolemToken;

/**
 *
 * @author North
 */
public final class SensorSplicer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Golem creatures");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new SubtypePredicate(SubType.GOLEM));
    }

    public SensorSplicer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Sensor Splicer enters the battlefield, create a 3/3 colorless Golem artifact creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new GolemToken(expansionSetCode))));

        // Golem creatures you control have vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter)));
    }

    public SensorSplicer(final SensorSplicer card) {
        super(card);
    }

    @Override
    public SensorSplicer copy() {
        return new SensorSplicer(this);
    }
}
