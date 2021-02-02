
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
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
public final class AqualishBountyHunter extends CardImpl {

    public AqualishBountyHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.AQUALISH);
        this.subtype.add(SubType.HUNTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // <i>Bounty</i> &mdash; Whenever a creature an opponent controls with a bounty counter on it dies, target player discards a card.
        Ability ability = new BountyAbility(new DiscardTargetEffect(1));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private AqualishBountyHunter(final AqualishBountyHunter card) {
        super(card);
    }

    @Override
    public AqualishBountyHunter copy() {
        return new AqualishBountyHunter(this);
    }
}
