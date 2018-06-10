
package mage.cards.u;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
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
import mage.game.events.GameEvent.EventType;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
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
                KickedCondition.instance,
                "If this spell was kicked, it deals 5 damage to target player or planeswalker"));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            if (KickedCondition.instance.apply(game, ability)) {
                ability.addTarget(new TargetPlayerOrPlaneswalker());
            }
        }
    }

    public UnstableFooting(final UnstableFooting card) {
        super(card);
    }

    @Override
    public UnstableFooting copy() {
        return new UnstableFooting(this);
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
        return event.getType() == EventType.PREVENT_DAMAGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

}
