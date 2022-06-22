
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward
 */
public final class IncreasingSavagery extends CardImpl {

    public IncreasingSavagery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}{G}");


        // Put five +1/+1 counters on target creature. If this spell was cast from a graveyard, put ten +1/+1 counters on that creature instead.
        this.getSpellAbility().addEffect(new IncreasingSavageryEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Flashback {5}{G}{G}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{G}{G}")));
    }

    private IncreasingSavagery(final IncreasingSavagery card) {
        super(card);
    }

    @Override
    public IncreasingSavagery copy() {
        return new IncreasingSavagery(this);
    }
}

class IncreasingSavageryEffect extends OneShotEffect {

    public IncreasingSavageryEffect() {
        super(Outcome.BoostCreature);
        staticText = "Put five +1/+1 counters on target creature. If this spell was cast from a graveyard, put ten +1/+1 counters on that creature instead";
    }

    public IncreasingSavageryEffect(final IncreasingSavageryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = 5;
        Spell spell = (Spell) game.getStack().getStackObject(source.getSourceId());
        if (spell != null) {
            if (spell.getFromZone() == Zone.GRAVEYARD) {
                amount = 10;
            }
            Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(amount), source.getControllerId(), source, game);
            }
            return true;
        }
        return false;
    }

    @Override
    public IncreasingSavageryEffect copy() {
        return new IncreasingSavageryEffect(this);
    }

}
