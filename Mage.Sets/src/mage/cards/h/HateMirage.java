package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.Collection;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HateMirage extends CardImpl {

    public HateMirage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Choose up to two target creatures you don't control. For each of those creatures, create a token that's a copy of that creature. Those tokens gain haste. Exile them at the beginning of the next end step.
        this.getSpellAbility().addEffect(new HateMirageEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL, false));
    }

    private HateMirage(final HateMirage card) {
        super(card);
    }

    @Override
    public HateMirage copy() {
        return new HateMirage(this);
    }
}

class HateMirageEffect extends OneShotEffect {

    HateMirageEffect() {
        super(Outcome.Benefit);
        staticText = "Choose up to two target creatures you don't control. " +
                "For each of those creatures, create a token that's a copy of that creature. " +
                "Those tokens gain haste. Exile them at the beginning of the next end step.";
    }

    private HateMirageEffect(final HateMirageEffect effect) {
        super(effect);
    }

    @Override
    public HateMirageEffect copy() {
        return new HateMirageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        source.getTargets()
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .forEach(uuid -> {
                    CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                            source.getControllerId(), null, true
                    );
                    effect.setTargetPointer(new FixedTarget(uuid, game));
                    effect.apply(game, source);
                    effect.exileTokensCreatedAtNextEndStep(game, source);
                });
        return true;
    }
}
