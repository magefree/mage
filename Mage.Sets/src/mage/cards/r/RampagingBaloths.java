

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BeastToken2;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class RampagingBaloths extends CardImpl {

    public RampagingBaloths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new BeastToken2()), true));
    }

    private RampagingBaloths(final RampagingBaloths card) {
        super(card);
    }

    @Override
    public RampagingBaloths copy() {
        return new RampagingBaloths(this);
    }

}
