package mage.cards.s;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jimga150
 */
public final class SplashPortal extends CardImpl {

    public SplashPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U}");
        

        // Exile target creature you control, then return it to the battlefield under its owner's control.
        // If that creature is a Bird, Frog, Otter, or Rat, draw a card.
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileThenReturnTargetEffect(false, false, new SplashPortalEffect()));
    }

    private SplashPortal(final SplashPortal card) {
        super(card);
    }

    @Override
    public SplashPortal copy() {
        return new SplashPortal(this);
    }
}

class SplashPortalEffect extends OneShotEffect {

    private static final List<SubType> checkedSubTypes = Arrays.asList(
            SubType.BIRD, SubType.FROG, SubType.OTTER, SubType.RAT
    );

    SplashPortalEffect() {
        super(Outcome.Benefit);
        staticText = "If that creature is a Bird, Frog, Otter, or Rat, draw a card.";
    }

    private SplashPortalEffect(final SplashPortalEffect effect) {
        super(effect);
    }

    @Override
    public SplashPortalEffect copy() {
        return new SplashPortalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null){
                continue;
            }
            boolean hasSubType = checkedSubTypes.stream()
                    .anyMatch(subType -> permanent.hasSubtype(subType, game));
            if (hasSubType) {
                Effect effect = new DrawCardSourceControllerEffect(1);
                return effect.apply(game, source);
            }
        }
        return true;
    }
}