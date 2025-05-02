package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.CragflameToken;

/**
 *
 * @author TheElk801
 */
public final class MabelHeirToCragflame extends CardImpl {

    private static final FilterCreaturePermanent filter=new FilterCreaturePermanent(SubType.MOUSE,"Mice");
    public MabelHeirToCragflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other Mice you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1,1, Duration.WhileOnBattlefield,filter,true
        )));

        // When Mabel, Heir to Cragflame enters, create Cragflame, a legendary colorless Equipment artifact token with "Equipped creature gets +1/+1 and has vigilance, trample, and haste" and equip {2}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new CragflameToken())));
    }

    private MabelHeirToCragflame(final MabelHeirToCragflame card) {
        super(card);
    }

    @Override
    public MabelHeirToCragflame copy() {
        return new MabelHeirToCragflame(this);
    }
}
