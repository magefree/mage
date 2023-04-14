
package mage.cards.m;

import java.util.UUID;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.GoblinToken;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author markedagain
 */
public final class MoggAlarm extends CardImpl {

     private static final FilterControlledPermanent filter = new FilterControlledPermanent("Mountains");
    static {
        filter.add(SubType.MOUNTAIN.getPredicate());
    }
    
    public MoggAlarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}{R}");

        // You may sacrifice two Mountains rather than pay Mogg Alarm's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new SacrificeTargetCost(new TargetControlledPermanent(2, 2, filter, true))));
        // Create two 1/1 red Goblin creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new GoblinToken(),2));
        
    }

    private MoggAlarm(final MoggAlarm card) {
        super(card);
    }

    @Override
    public MoggAlarm copy() {
        return new MoggAlarm(this);
    }
}
