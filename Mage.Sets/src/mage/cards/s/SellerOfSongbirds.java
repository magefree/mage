
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BirdToken;

/**
 * @author LevelX2
 */
public final class SellerOfSongbirds extends CardImpl {

    public SellerOfSongbirds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);


        // When Seller of Songbirds enters the battlefield, create a 1/1 white Bird creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BirdToken())));
    }

    private SellerOfSongbirds(final SellerOfSongbirds card) {
        super(card);
    }

    @Override
    public SellerOfSongbirds copy() {
        return new SellerOfSongbirds(this);
    }
}
