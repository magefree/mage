
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AuraAttachedTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author LevelX2
 */
public final class BrambleElemental extends CardImpl {

    public BrambleElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever an Aura becomes attached to Bramble Elemental, create two 1/1 green Saproling creature tokens.
        this.addAbility(new AuraAttachedTriggeredAbility(new CreateTokenEffect(new SaprolingToken(),2),false));
    }

    private BrambleElemental(final BrambleElemental card) {
        super(card);
    }

    @Override
    public BrambleElemental copy() {
        return new BrambleElemental(this);
    }
}
