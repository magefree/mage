package mage.cards.s;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileThenReturnTargetEffect;
import mage.cards.*;
import mage.constants.CardType;
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
        this.getSpellAbility().addEffect(new ExileThenReturnTargetEffect(false, false).withAfterEffect(
                new ConditionalOneShotEffect(new DrawCardSourceControllerEffect(1), new SplashPortalCondition())
        ));
    }

    private SplashPortal(final SplashPortal card) {
        super(card);
    }

    @Override
    public SplashPortal copy() {
        return new SplashPortal(this);
    }
}

// Based on TargetHasSubtypeCondition
class SplashPortalCondition implements Condition {

    private static final List<SubType> checkedSubTypes = Arrays.asList(
            SubType.BIRD, SubType.FROG, SubType.OTTER, SubType.RAT
    );

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getTargets().isEmpty()) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        return checkedSubTypes.stream()
                .anyMatch(subType -> permanent.hasSubtype(subType, game));
    }

    @Override
    public String toString() {
        return "If that creature is a Bird, Frog, Otter, or Rat";
    }
}