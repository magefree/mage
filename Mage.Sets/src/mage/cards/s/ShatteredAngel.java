

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author Loki
 */
public final class ShatteredAngel extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("a land");
    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ShatteredAngel (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever a land enters the battlefield under an opponent's control, you may gain 3 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                Zone.BATTLEFIELD, new GainLifeEffect(3), filter, true, "Whenever a land enters the battlefield under an opponent's control, you may gain 3 life."));
    }

    public ShatteredAngel (final ShatteredAngel card) {
        super(card);
    }

    @Override
    public ShatteredAngel copy() {
        return new ShatteredAngel(this);
    }

}
