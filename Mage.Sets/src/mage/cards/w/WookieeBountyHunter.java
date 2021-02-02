
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.BountyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class WookieeBountyHunter extends CardImpl {

    public WookieeBountyHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.WOOKIEE);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // <i>Bounty</i> &mdash; Whenever a creature an opponent controls with a bounty counter on it dies, you gain 3 life.
        this.addAbility(new BountyAbility(new GainLifeEffect(3)));
    }

    private WookieeBountyHunter(final WookieeBountyHunter card) {
        super(card);
    }

    @Override
    public WookieeBountyHunter copy() {
        return new WookieeBountyHunter(this);
    }
}
