

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DarkslickDrake extends CardImpl {

    public DarkslickDrake (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new DiesSourceTriggeredAbility(new DrawCardSourceControllerEffect(1), false));
    }

    private DarkslickDrake(final DarkslickDrake card) {
        super(card);
    }

    @Override
    public DarkslickDrake copy() {
        return new DarkslickDrake(this);
    }
}
