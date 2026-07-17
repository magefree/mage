package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.token.HeroToken;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class AerithRescueMission extends CardImpl {

    public AerithRescueMission(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Choose one--
        // * Take the Elevator -- Create three 1/1 colorless Hero creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new HeroToken(), 3));
        this.getSpellAbility().withFirstModeFlavorWord("Take the Elevator");

        // * Take 59 Flights of Stairs -- Tap up to three target creatures. Put a stun counter on one of them.
        this.getSpellAbility().addMode(new Mode(new TapTargetEffect())
                .addEffect(new AerithRescueMissionStunEffect())
                .addTarget(new TargetCreaturePermanent(0, 3))
                .withFlavorWord("Take 59 Flights of Stairs"));
    }

    private AerithRescueMission(final AerithRescueMission card) {
        super(card);
    }

    @Override
    public AerithRescueMission copy() {
        return new AerithRescueMission(this);
    }
}

class AerithRescueMissionStunEffect extends OneShotEffect {

    AerithRescueMissionStunEffect() {
        super(Outcome.Detriment);
        staticText = "Put a stun counter on one of them";
    }

    private AerithRescueMissionStunEffect(final AerithRescueMissionStunEffect effect) {
        super(effect);
    }

    @Override
    public AerithRescueMissionStunEffect copy() {
        return new AerithRescueMissionStunEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent filter = new FilterPermanent("creature to put a stun counter on");
        filter.add(new PermanentReferenceInCollectionPredicate(this.getTargetPointer().getTargets(game, source).stream()
                .map(game::getPermanent).collect(Collectors.toList()), game));
        Target target = new TargetPermanent(filter).withNotTarget(true);
        if (target.choose(Outcome.UnboostCreature, source.getControllerId(), source, game)) {
            Effect eff = new AddCountersTargetEffect(CounterType.STUN.createInstance());
            eff.setTargetPointer(new FixedTarget(target.getFirstTarget(), game));
            return eff.apply(game, source);
        }
        return false;
    }
}
