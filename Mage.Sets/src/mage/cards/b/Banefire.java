package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class Banefire extends CardImpl {

    public Banefire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}");

        // Banefire deals X damage to any target.       
        this.getSpellAbility().addEffect(new BaneFireEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // If X is 5 or more, Banefire can't be countered and the damage can't be prevented.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new BanefireCantCounterEffect()));
    }

    private Banefire(final Banefire card) {
        super(card);
    }

    @Override
    public Banefire copy() {
        return new Banefire(this);
    }
}

class testCondition implements Condition {

    private final DynamicValue xValue;
    private final int limit;

    public testCondition(DynamicValue xValue, int limit) {
        this.xValue = xValue;
        this.limit = limit;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) game.getStack().getStackObject(source.getSourceId());
        if (spell != null) {
            return (xValue.calculate(game, spell.getSpellAbility(), null) >= limit);
        }
        return false;

    }
}

class BaneFireEffect extends OneShotEffect {

    public BaneFireEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals X damage to any target";
    }

    public BaneFireEffect(final BaneFireEffect effect) {
        super(effect);
    }

    @Override
    public BaneFireEffect copy() {
        return new BaneFireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        int damage = source.getManaCostsToPay().getX();
        boolean preventable = damage < 5;
        if (targetPlayer != null) {
            targetPlayer.damage(damage, source.getSourceId(), source, game, false, preventable);
            return true;
        }
        if (targetCreature != null) {
            targetCreature.damage(damage, source.getSourceId(), source, game, false, preventable);
            return true;
        }
        return false;
    }
}

class BanefireCantCounterEffect extends ContinuousRuleModifyingEffectImpl {

    private Condition condition = new testCondition(ManacostVariableValue.REGULAR, 5);

    public BanefireCantCounterEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit);
        staticText = "If X is 5 or more, this spell can't be countered and the damage can't be prevented";
    }

    public BanefireCantCounterEffect(final BanefireCantCounterEffect effect) {
        super(effect);
        this.condition = effect.condition;
    }

    @Override
    public BanefireCantCounterEffect copy() {
        return new BanefireCantCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() != GameEvent.EventType.COUNTER) { return false; }

        Card card = game.getCard(source.getSourceId());
        if (card == null) { return false; }

        UUID spellId = card.getSpellAbility().getId();
        if (!event.getTargetId().equals(spellId)) { return false; }

        return condition.apply(game, source);
    }
}
