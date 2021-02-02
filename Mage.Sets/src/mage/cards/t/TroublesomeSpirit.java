
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class TroublesomeSpirit extends CardImpl {

    public TroublesomeSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your end step, tap all lands you control.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new TapAllEffect(new FilterControlledLandPermanent("lands you control")), TargetController.YOU, false));
    }

    private TroublesomeSpirit(final TroublesomeSpirit card) {
        super(card);
    }

    @Override
    public TroublesomeSpirit copy() {
        return new TroublesomeSpirit(this);
    }
}
