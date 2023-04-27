
package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class UnstableFooting extends CardImpl {

    public UnstableFooting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}");

        // Kicker {3}{R} (You may pay an additional {3}{R} as you cast this spell.)
        this.addAbility(new KickerAbility("{3}{R}"));

        // Damage can't be prevented this turn. If Unstable Footing was kicked, it deals 5 damage to target player.
        this.getSpellAbility().addEffect(new UnstableFootingEffect());
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(5),
                KickedCondition.ONCE,
                "If this spell was kicked, it deals 5 damage to target player or planeswalker")
        );
        this.getSpellAbility().setTargetAdjuster(UnstableFootingAdjuster.instance);

    }

    private UnstableFooting(final UnstableFooting card) {
        super(card);
    }

    @Override
    public UnstableFooting copy() {
        return new UnstableFooting(this);
    }
}

enum UnstableFootingAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        if (KickedCondition.ONCE.apply(game, ability)) {
            ability.addTarget(new TargetPlayerOrPlaneswalker());
        }
    }
}

class UnstableFootingEffect extends ReplacementEffectImpl {

    public UnstableFootingEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Damage can't be prevented this turn";
    }

    public UnstableFootingEffect(final UnstableFootingEffect effect) {
        super(effect);
    }

    @Override
    public UnstableFootingEffect copy() {
        return new UnstableFootingEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PREVENT_DAMAGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

}
