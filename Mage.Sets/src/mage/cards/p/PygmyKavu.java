
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author LoneFox
 */
public final class PygmyKavu extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("black creature your opponents control");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public PygmyKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Pygmy Kavu enters the battlefield, draw a card for each black creature your opponents control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter))));
    }

    private PygmyKavu(final PygmyKavu card) {
        super(card);
    }

    @Override
    public PygmyKavu copy() {
        return new PygmyKavu(this);
    }
}
