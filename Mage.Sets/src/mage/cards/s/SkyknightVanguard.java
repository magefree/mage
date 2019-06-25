package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyknightVanguard extends CardImpl {

    public SkyknightVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Skyknight Vanguard attacks, create a 1/1 white Soldier creature token that's tapped and attacking.
        this.addAbility(new AttacksTriggeredAbility(
                new CreateTokenEffect(new SoldierToken(), 1, true, true), false
        ));
    }

    private SkyknightVanguard(final SkyknightVanguard card) {
        super(card);
    }

    @Override
    public SkyknightVanguard copy() {
        return new SkyknightVanguard(this);
    }
}
