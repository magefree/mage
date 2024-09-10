package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.SuspectedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeadlyComplication extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("suspected creature you control");

    static {
        filter.add(SuspectedPredicate.instance);
    }

    public DeadlyComplication(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{R}");

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Destroy target creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Put a +1/+1 counter on target suspected creature you control. You may have it become no longer suspected.
        this.getSpellAbility().addMode(new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance()))
                .addEffect(new DeadlyComplicationEffect())
                .addTarget(new TargetPermanent(filter)));
    }

    private DeadlyComplication(final DeadlyComplication card) {
        super(card);
    }

    @Override
    public DeadlyComplication copy() {
        return new DeadlyComplication(this);
    }
}

class DeadlyComplicationEffect extends OneShotEffect {

    DeadlyComplicationEffect() {
        super(Outcome.Benefit);
        staticText = "You may have it become no longer suspected";
    }

    private DeadlyComplicationEffect(final DeadlyComplicationEffect effect) {
        super(effect);
    }

    @Override
    public DeadlyComplicationEffect copy() {
        return new DeadlyComplicationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player != null && permanent != null && player.chooseUse(
                outcome, "Have " + permanent.getName() + " no longer be suspected?", source, game
        )) {
            permanent.setSuspected(false, game, source);
            return true;
        }
        return false;
    }
}
