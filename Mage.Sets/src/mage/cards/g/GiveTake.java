
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GiveTake extends SplitCard {

    public GiveTake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}", "{2}{U}", SpellAbilityType.SPLIT_FUSED);

        // Give
        // Put three +1/+1 counters on target creature.
        getLeftHalfCard().getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(3)));
        getLeftHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent().withChooseHint("to put counters on"));

        // Take
        // Remove all +1/+1 counters from target creature you control. Draw that many cards.
        getRightHalfCard().getSpellAbility().addEffect(new TakeEffect());
        getRightHalfCard().getSpellAbility().addTarget(new TargetControlledCreaturePermanent().withChooseHint("to remove counters from"));
    }

    private GiveTake(final GiveTake card) {
        super(card);
    }

    @Override
    public GiveTake copy() {
        return new GiveTake(this);
    }
}

class TakeEffect extends OneShotEffect {

    public TakeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Remove all +1/+1 counters from target creature you control. Draw that many cards";
    }

    public TakeEffect(final TakeEffect effect) {
        super(effect);
    }

    @Override
    public TakeEffect copy() {
        return new TakeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature != null) {
            int numberCounters = creature.getCounters(game).getCount(CounterType.P1P1);
            if (numberCounters > 0) {
                creature.removeCounters(CounterType.P1P1.getName(), numberCounters, source, game);
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    controller.drawCards(numberCounters, source, game);
                } else {
                    throw new UnsupportedOperationException("Controller missing");
                }
            }
            return true;
        }
        return false;
    }
}
