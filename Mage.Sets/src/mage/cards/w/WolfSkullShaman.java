
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.abilityword.KinshipAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.WolfToken;

/**
 *
 * @author LevelX2
 */
public final class WolfSkullShaman extends CardImpl {
    
    public WolfSkullShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kinship - At the beginning of your upkeep, you may look at the top card of your library. If it shares a creature type with Wolf-Skull Shaman, you may reveal it. If you do, create a 2/2 green Wolf creature token.
        this.addAbility(new KinshipAbility(new CreateTokenEffect(new WolfToken())));
    }

    private WolfSkullShaman(final WolfSkullShaman card) {
        super(card);
    }

    @Override
    public WolfSkullShaman copy() {
        return new WolfSkullShaman(this);
    }
}
