
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class SanityGnawers extends CardImpl {

    public SanityGnawers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{R}");
        this.subtype.add(SubType.RAT);


        
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Sanity Gnawers enters the battlefield, target player discards a card at random.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(1, true), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        
    }

    private SanityGnawers(final SanityGnawers card) {
        super(card);
    }

    @Override
    public SanityGnawers copy() {
        return new SanityGnawers(this);
    }
}
