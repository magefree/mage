package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NewWayForward extends CardImpl {

    public NewWayForward(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}{R}{W}");

        // The next time a source of your choice would deal damage to you this turn, prevent that damage. When damage is prevented this way, New Way Forward deals that much damage to that source's controller and you draw that many cards.
        this.getSpellAbility().addEffect(new NewWayForwardEffect());
    }

    private NewWayForward(final NewWayForward card) {
        super(card);
    }

    @Override
    public NewWayForward copy() {
        return new NewWayForward(this);
    }
}

class NewWayForwardEffect extends PreventionEffectImpl {

    private final TargetSource target;

    NewWayForwardEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, false, false);
        this.staticText = "the next time a source of your choice would deal damage to you this turn, " +
                "prevent that damage. When damage is prevented this way, " +
                "{this} deals that much damage to that source's controller and you draw that many cards";
        this.target = new TargetSource();
    }

    private NewWayForwardEffect(final NewWayForwardEffect effect) {
        super(effect);
        this.target = effect.target.copy();
    }

    @Override
    public NewWayForwardEffect copy() {
        return new NewWayForwardEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        this.used = true;
        this.discard(); // only one use
        if (preventionData.getPreventedDamage() < 1) {
            return true;
        }
        UUID objectControllerId = game.getControllerId(target.getFirstTarget());
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(preventionData.getPreventedDamage())
                        .setTargetPointer(new FixedTarget(objectControllerId)),
                false
        );
        ability.addEffect(new DrawCardSourceControllerEffect(preventionData.getPreventedDamage()));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !this.used
                && super.applies(event, source, game)
                && event.getTargetId().equals(source.getControllerId())
                && event.getSourceId().equals(target.getFirstTarget());
    }
}
