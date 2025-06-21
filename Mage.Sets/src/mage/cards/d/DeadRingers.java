package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LoneFox
 */
public final class DeadRingers extends CardImpl {

    public DeadRingers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Destroy two target nonblack creatures unless either one is a color the other isn't. They can't be regenerated.
        this.getSpellAbility().addEffect(new DeadRingersEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(2, StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
    }

    private DeadRingers(final DeadRingers card) {
        super(card);
    }

    @Override
    public DeadRingers copy() {
        return new DeadRingers(this);
    }
}

class DeadRingersEffect extends OneShotEffect {

    DeadRingersEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy two target nonblack creatures unless either one is a color the other isn't. They can't be regenerated.";
    }

    private DeadRingersEffect(final DeadRingersEffect effect) {
        super(effect);
    }

    @Override
    public DeadRingersEffect copy() {
        return new DeadRingersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (permanents.size() < 2) {
            return false;
        }
        Permanent first = permanents.get(0);
        Permanent second = permanents.get(1);
        if (!first.getColor(game).equals(second.getColor(game))) {
            return false;
        }
        first.destroy(source, game, true);
        second.destroy(source, game, true);
        return true;
    }
}
