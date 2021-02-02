
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BountyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author Styxo
 */
public final class NoviceBountyHunter extends CardImpl {

    public NoviceBountyHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // <i>Bounty</i> &mdash; Whenever a creature an opponent controls with a bounty counter on it dies, Novice Bounty Hunter deals 2 damge to target player.
        Ability ability = new BountyAbility(new DamageTargetEffect(2));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private NoviceBountyHunter(final NoviceBountyHunter card) {
        super(card);
    }

    @Override
    public NoviceBountyHunter copy() {
        return new NoviceBountyHunter(this);
    }
}
