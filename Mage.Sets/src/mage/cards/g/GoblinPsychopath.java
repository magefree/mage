package mage.cards.g;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.StaticHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author L_J
 */
public final class GoblinPsychopath extends CardImpl {

    public GoblinPsychopath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Goblin Psychopath attacks or blocks, flip a coin. If you lose the flip, the next time it would deal combat damage this turn, it deals that damage to you instead.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new GoblinPsychopathEffect(), false));
    }

    private GoblinPsychopath(final GoblinPsychopath card) {
        super(card);
    }

    @Override
    public GoblinPsychopath copy() {
        return new GoblinPsychopath(this);
    }
}

class GoblinPsychopathEffect extends ReplacementEffectImpl {

    private boolean wonFlip;

    public GoblinPsychopathEffect() {
        super(Duration.EndOfTurn, Outcome.RedirectDamage);
        staticText = "flip a coin. If you lose the flip, the next time it would deal combat damage this turn, it deals that damage to you instead";
    }

    private GoblinPsychopathEffect(final GoblinPsychopathEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            discard();
            return;
        }

        this.wonFlip = controller.flipCoin(source, game, true);
        game.informPlayers("The next time " + game.getObject(source.getSourceId()).getLogName()
                + " would deal combat damage this turn, it deals that damage to " + controller.getLogName()
                + " instead.");
    }

    @Override
    public GoblinPsychopathEffect copy() {
        return new GoblinPsychopathEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        if (object == null) {
            game.informPlayers("Couldn't find source of damage");
            return false;
        }
        return event.getSourceId().equals(source.getSourceId());
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        MageObject object = game.getObject(event.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || object == null
                || !(this.applies(event, source, game) && event instanceof DamageEvent && event.getAmount() > 0)) {
            return false;
        }
        DamageEvent damageEvent = (DamageEvent) event;
        if (!damageEvent.isCombatDamage() || wonFlip) {
            return false;
        }
        // TODO: make this redirect damage from all blockers
        controller.damage(event.getAmount(), source.getSourceId(), source, game);
        String sourceLogName = game.getObject(source).getLogName() + ": ";
        game.informPlayers(sourceLogName + "Redirected " + event.getAmount() + " damage to " + controller.getLogName());
        this.discard();
        return true;
    }

    @Override
    public boolean hasHint() {
        return true;
    }

    @Override
    public List<Hint> getAffectedHints(Permanent permanent, Ability source, Game game) {
        if (wonFlip || !permanent.getId().equals(source.getSourceId())) {
            return Collections.emptyList();
        }

        return Arrays.asList(
                new StaticHint("The next time {this} would deal combat damage this turn, it deals that damage to "
                        + game.getPlayer(source.getControllerId()).getLogName() + " instead."));
    }
}
