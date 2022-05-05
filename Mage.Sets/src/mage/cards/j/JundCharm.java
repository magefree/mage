
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jonubuu
 */
public final class JundCharm extends CardImpl {

    public JundCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}{R}{G}");


        // Choose one - Exile all cards from target player's graveyard;
        this.getSpellAbility().addEffect(new ExileGraveyardAllTargetPlayerEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
        // or Jund Charm deals 2 damage to each creature;
        Mode mode = new Mode(new DamageAllEffect(2, new FilterCreaturePermanent()));
        this.getSpellAbility().addMode(mode);
        // or put two +1/+1 counters on target creature.
        mode = new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2), Outcome.BoostCreature));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private JundCharm(final JundCharm card) {
        super(card);
    }

    @Override
    public JundCharm copy() {
        return new JundCharm(this);
    }
}
