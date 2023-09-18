

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.DragonToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BroodmateDragon extends CardImpl {

    private static final DragonToken dragonToken = new DragonToken();

    public BroodmateDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{R}{G}");
        this.subtype.add(SubType.DRAGON);


        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(dragonToken), false));
    }

    private BroodmateDragon(final BroodmateDragon card) {
        super(card);
    }

    @Override
    public BroodmateDragon copy() {
        return new BroodmateDragon(this);
    }

}
