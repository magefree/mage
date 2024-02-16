
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ZombieToken;

/**
 * @author Loki
 */
public final class GrixisSlavedriver extends CardImpl {

    public GrixisSlavedriver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new CreateTokenEffect(new ZombieToken()), false));
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{3}{B}")));
    }

    private GrixisSlavedriver(final GrixisSlavedriver card) {
        super(card);
    }

    @Override
    public GrixisSlavedriver copy() {
        return new GrixisSlavedriver(this);
    }

}
