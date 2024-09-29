package mage.cards.c;

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
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class CoordinatedClobbering extends CardImpl {

    public CoordinatedClobbering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Tap one or two target untapped creatures you control. They each deal damage equal to their power to target creature an opponent controls.
        this.getSpellAbility().addEffect(new CoordinatedClobberingEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(
                1, 2, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURES
        ));
        this.getSpellAbility().addTarget(new TargetOpponentsCreaturePermanent());
    }

    private CoordinatedClobbering(final CoordinatedClobbering card) {
        super(card);
    }

    @Override
    public CoordinatedClobbering copy() {
        return new CoordinatedClobbering(this);
    }
}

class CoordinatedClobberingEffect extends OneShotEffect {

    CoordinatedClobberingEffect() {
        super(Outcome.Benefit);
        staticText = "tap one or two target untapped creatures you control. " +
                "They each deal damage equal to their power to target creature an opponent controls";
    }

    private CoordinatedClobberingEffect(final CoordinatedClobberingEffect effect) {
        super(effect);
    }

    @Override
    public CoordinatedClobberingEffect copy() {
        return new CoordinatedClobberingEffect(this);
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
        if (permanents.isEmpty()) {
            return false;
        }
        for (Permanent permanent : permanents) {
            permanent.tap(source, game);
        }
        Permanent targetPermanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (targetPermanent == null) {
            return true;
        }
        game.processAction();
        for (Permanent permanent : permanents) {
            targetPermanent.damage(permanent.getPower().getValue(), permanent.getId(), source, game);
        }
        return true;
    }
}
// flame on!
