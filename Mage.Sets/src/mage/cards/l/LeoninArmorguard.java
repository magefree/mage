
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class LeoninArmorguard extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");
    
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public LeoninArmorguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);



        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Leonin Armorguard enters the battlefield, creatures you control get +1/+1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(1, 1, Duration.EndOfTurn, filter, false)));
    }

    private LeoninArmorguard(final LeoninArmorguard card) {
        super(card);
    }

    @Override
    public LeoninArmorguard copy() {
        return new LeoninArmorguard(this);
    }
}
