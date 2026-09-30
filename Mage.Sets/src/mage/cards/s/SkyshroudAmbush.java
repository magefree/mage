package mage.cards.s;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author muz
 */
public final class SkyshroudAmbush extends CardImpl {

    public SkyshroudAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature you control fights target creature you don't control. When the creature you control wins the fight, draw a card.
        this.getSpellAbility().addEffect(new SkyshroudAmbushEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
    }

    private SkyshroudAmbush(final SkyshroudAmbush card) {
        super(card);
    }

    @Override
    public SkyshroudAmbush copy() {
        return new SkyshroudAmbush(this);
    }
}

class SkyshroudAmbushEffect extends OneShotEffect {

    SkyshroudAmbushEffect() {
        super(Outcome.Benefit);
        staticText = "Target creature you control fights target creature you don't control. "
            + "When the creature you control wins the fight, draw a card.";
    }

    private SkyshroudAmbushEffect(final SkyshroudAmbushEffect effect) {
        super(effect);
    }

    @Override
    public SkyshroudAmbushEffect copy() {
        return new SkyshroudAmbushEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent controlled = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Permanent uncontrolled = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (controlled == null || uncontrolled == null
                || !controlled.isCreature(game) || !uncontrolled.isCreature(game)) {
            return false;
        }
        MageObjectReference controlledRef = new MageObjectReference(controlled, game);
        MageObjectReference uncontrolledRef = new MageObjectReference(uncontrolled, game);
        controlled.fight(uncontrolled, source, game);
        game.processAction();
        // "Wins the fight": the creature you control survived and the creature you don't control did not
        if (controlledRef.getPermanent(game) != null && uncontrolledRef.getPermanent(game) == null) {
            game.fireReflexiveTriggeredAbility(
                new ReflexiveTriggeredAbility(
                    new DrawCardSourceControllerEffect(1),
                    false,
                    "when the creature you control wins the fight, draw a card"
                ),
                source
            );
        }
        return true;
    }
}
