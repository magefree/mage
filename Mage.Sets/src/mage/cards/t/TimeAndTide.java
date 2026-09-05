package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PhaseOutAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public final class TimeAndTide extends CardImpl {

    public TimeAndTide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        //Simultaneously, all phased-out creatures phase in and all creatures with phasing phase out.
        this.getSpellAbility().addEffect(new TimeAndTideEffect());
    }

    private TimeAndTide(final TimeAndTide card) {
        super(card);
    }

    @Override
    public TimeAndTide copy() {
        return new TimeAndTide(this);
    }
}

class TimeAndTideEffect extends OneShotEffect {

    TimeAndTideEffect() {
        super(Outcome.Neutral);
        this.staticText = "Simultaneously, all phased-out creatures phase in and all creatures with phasing phase out.";
    }

    private TimeAndTideEffect(final TimeAndTideEffect effect) {
        super(effect);
    }

    @Override
    public TimeAndTideEffect copy() {
        return new TimeAndTideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        final Set<Permanent> phasein = game.getBattlefield().getPhasedOut(null).stream().filter(Permanent::isCreature).collect(Collectors.toSet());
        final List<UUID> phaseout = game.getBattlefield().getPhasingOut(game, null).stream().filter(Permanent::isCreature).map(Permanent::getId).collect(Collectors.toList());
        return new PhaseOutAllEffect(phaseout).apply(game, source) && phasein.stream().allMatch(perm -> perm.phaseIn(game));
    }
}
