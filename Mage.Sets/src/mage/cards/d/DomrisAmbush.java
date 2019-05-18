package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageWithPowerTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DomrisAmbush extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreatureOrPlaneswalkerPermanent("creature or planeswalker you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public DomrisAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}{G}");

        // Put a +1/+1 counter on target creature you control. Then that creature deals damage equal to its power to target creature or planeswalker you don't control.
        this.getSpellAbility().addEffect(new DomrisAmbushEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private DomrisAmbush(final DomrisAmbush card) {
        super(card);
    }

    @Override
    public DomrisAmbush copy() {
        return new DomrisAmbush(this);
    }
}

class DomrisAmbushEffect extends OneShotEffect {

    DomrisAmbushEffect() {
        super(Outcome.Benefit);
        staticText = "Put a +1/+1 counter on target creature you control. " +
                "Then that creature deals damage equal to its power " +
                "to target creature or planeswalker you don't control.";
    }

    private DomrisAmbushEffect(final DomrisAmbushEffect effect) {
        super(effect);
    }

    @Override
    public DomrisAmbushEffect copy() {
        return new DomrisAmbushEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
        game.applyEffects();
        return new DamageWithPowerTargetEffect().apply(game, source);
    }
}
