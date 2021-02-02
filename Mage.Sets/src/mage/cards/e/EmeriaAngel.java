

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BirdToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class EmeriaAngel extends CardImpl {

    public EmeriaAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new BirdToken()), true));
    }

    private EmeriaAngel(final EmeriaAngel card) {
        super(card);
    }

    @Override
    public EmeriaAngel copy() {
        return new EmeriaAngel(this);
    }

}
