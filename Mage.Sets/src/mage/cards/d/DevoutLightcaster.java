
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author North
 */
public final class DevoutLightcaster extends CardImpl {

    private static final FilterPermanent filterTarget = new FilterPermanent("black permanent");

    static {
        filterTarget.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public DevoutLightcaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetPermanent(filterTarget));
        this.addAbility(ability);
    }

    private DevoutLightcaster(final DevoutLightcaster card) {
        super(card);
    }

    @Override
    public DevoutLightcaster copy() {
        return new DevoutLightcaster(this);
    }
}
