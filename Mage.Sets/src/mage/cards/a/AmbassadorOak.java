
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ElfToken;

/**
 *
 * @author Loki
 */
public final class AmbassadorOak extends CardImpl {

    public AmbassadorOak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ElfToken(), 1), false));
    }

    public AmbassadorOak(final AmbassadorOak card) {
        super(card);
    }

    @Override
    public AmbassadorOak copy() {
        return new AmbassadorOak(this);
    }
}
