package mage.cards.w;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class WickedSlumber extends CardImpl {

    public WickedSlumber(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Tap up to two target creatures. Put a stun counter on either of them. Then put a stun counter on either of them.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(new WickedSlumberEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private WickedSlumber(final WickedSlumber card) {
        super(card);
    }

    @Override
    public WickedSlumber copy() {
        return new WickedSlumber(this);
    }
}

class WickedSlumberEffect extends OneShotEffect {

    WickedSlumberEffect() {
        super(Outcome.Benefit);
        staticText = "put a stun counter on either of them. Then put a stun counter on either of them";
    }

    private WickedSlumberEffect(final WickedSlumberEffect effect) {
        super(effect);
    }

    @Override
    public WickedSlumberEffect copy() {
        return new WickedSlumberEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (player == null || permanents.isEmpty()) {
            return false;
        }
        if (permanents.size() == 1) {
            permanents.get(0).addCounters(CounterType.STUN.createInstance(), source, game);
            permanents.get(0).addCounters(CounterType.STUN.createInstance(), source, game);
            return true;
        }
        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.or(
                permanents
                        .stream()
                        .map(MageItem::getId)
                        .map(PermanentIdPredicate::new)
                        .collect(Collectors.toList())
        ));
        for (int i = 0; i < 2; i++) {
            TargetPermanent target = new TargetPermanent(filter);
            target.setNotTarget(true);
            target.withChooseHint("to add a stun counter to");
            player.choose(outcome, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            permanent.addCounters(CounterType.STUN.createInstance(), source, game);
        }
        return true;
    }
}
