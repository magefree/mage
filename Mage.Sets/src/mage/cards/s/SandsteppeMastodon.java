
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SandsteppeMastodon extends CardImpl {

    public SandsteppeMastodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}{G}");
        this.subtype.add(SubType.ELEPHANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // When Sandsteppe Mastodon enters the battlefield, bolster 5. (Choose a creature with the least toughness or tied with the least toughness among creatures you control. Put 5 +1/+1 counters on it.)
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BolsterEffect(5), false));
    }

    private SandsteppeMastodon(final SandsteppeMastodon card) {
        super(card);
    }

    @Override
    public SandsteppeMastodon copy() {
        return new SandsteppeMastodon(this);
    }
}

