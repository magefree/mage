package mage.cards.h;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TimingRule;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class HallowedRespite extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonlegendary creature");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public HallowedRespite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{U}");

        // Exile target nonlegendary creature, then return it to the battlefield under its owner's control. If it entered under your control, put a +1/+1 counter on it. Otherwise, tap it.
        this.getSpellAbility().addEffect(new ExileTargetForSourceEffect());
        this.getSpellAbility().addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false, "it").concatBy(", then"));
        this.getSpellAbility().addEffect(new HallowedRespiteEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // Flashback {1}{W}{U}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{1}{W}{U}")));
    }

    private HallowedRespite(final HallowedRespite card) {
        super(card);
    }

    @Override
    public HallowedRespite copy() {
        return new HallowedRespite(this);
    }
}

class HallowedRespiteEffect extends OneShotEffect {

    public HallowedRespiteEffect() {
        super(Outcome.Benefit);
        staticText = "If it entered under your control, put a +1/+1 counter on it. Otherwise, tap it";
    }

    private HallowedRespiteEffect(final HallowedRespiteEffect effect) {
        super(effect);
    }

    @Override
    public HallowedRespiteEffect copy() {
        return new HallowedRespiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        if (permanent.isControlledBy(source.getControllerId())) {
            permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        } else {
            permanent.tap(source, game);
        }
        return true;
    }
}
