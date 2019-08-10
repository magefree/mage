
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DiscardCardPlayerTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class TelekineticBonds extends CardImpl {

    public TelekineticBonds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}{U}");
        

        // Whenever a player discards a card, you may pay {1}{U}. If you do, you may tap or untap target permanent.
       Ability ability = new DiscardCardPlayerTriggeredAbility(new DoIfCostPaid(new MayTapOrUntapTargetEffect(), new ManaCostsImpl("{1}{U}")), true);
       ability.addTarget(new TargetPermanent());
       this.addAbility(ability);
        
    }

    public TelekineticBonds(final TelekineticBonds card) {
        super(card);
    }

    @Override
    public TelekineticBonds copy() {
        return new TelekineticBonds(this);
    }
}