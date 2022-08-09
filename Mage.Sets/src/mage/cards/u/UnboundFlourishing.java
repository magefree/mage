package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.mana.ActivatedManaAbilityImpl;
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
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class UnboundFlourishing extends CardImpl {

    final static String needPrefix = "_UnboundFlourishing_NeedCopy";

    public UnboundFlourishing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever you cast a permanent spell with a mana cost that contains {X}, double the value of X.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new UnboundFlourishingDoubleXEffect()));

        // Whenever you cast an instant or sorcery spell or activate an ability,
        // if that spell’s mana cost or that ability’s activation cost contains {X}, copy that spell or ability.
        // You may choose new targets for the copy.
        this.addAbility(new UnboundFlourishingCopyAbility());
    }

    private UnboundFlourishing(final UnboundFlourishing card) {
        super(card);
    }

    @Override
    public UnboundFlourishing copy() {
        return new UnboundFlourishing(this);
    }
}

class UnboundFlourishingDoubleXEffect extends ReplacementEffectImpl {

    UnboundFlourishingDoubleXEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, false);
        staticText = "Whenever you cast a permanent spell with a mana cost that contains {X}, double the value of X";
    }

    UnboundFlourishingDoubleXEffect(final UnboundFlourishingDoubleXEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.X_MANA_ANNOUNCE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        return spell != null && spell.isPermanent(game) && spell.isControlledBy(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public UnboundFlourishingDoubleXEffect copy() {
        return new UnboundFlourishingDoubleXEffect(this);
    }
}

class UnboundFlourishingCopyAbility extends TriggeredAbilityImpl {

    UnboundFlourishingCopyAbility() {
        super(Zone.BATTLEFIELD, new UnboundFlourishingCopyEffect(), false);
        setTriggerPhrase("Whenever you cast an instant or sorcery spell or activate an ability, " +
                         "if that spell's mana cost or that ability's activation cost contains {X}" );
    }

    UnboundFlourishingCopyAbility(final UnboundFlourishingCopyAbility ability) {
        super(ability);
    }

    @Override
    public UnboundFlourishingCopyAbility copy() {
        return new UnboundFlourishingCopyAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }

        // activated ability
        if (event.getType() == GameEvent.EventType.ACTIVATED_ABILITY) {
            StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
            if (stackAbility != null && !(stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl)) {
                if (stackAbility.getManaCostsToPay().containsX()) {
                    game.getState().setValue(this.getSourceId() + UnboundFlourishing.needPrefix, stackAbility);
                    return true;
                }
            }
        }

        // spell
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isInstantOrSorcery(game)) {
                if (spell.getSpellAbility().getManaCostsToPay().containsX()) {
                    game.getState().setValue(this.getSourceId() + UnboundFlourishing.needPrefix, spell);
                    return true;
                }
            }
        }
        return false;
    }
}

class UnboundFlourishingCopyEffect extends OneShotEffect {

    UnboundFlourishingCopyEffect() {
        super(Outcome.Benefit);
        this.staticText = ", copy that spell or ability. You may choose new targets for the copy";
    }

    UnboundFlourishingCopyEffect(final UnboundFlourishingCopyEffect effect) {
        super(effect);
    }

    @Override
    public UnboundFlourishingCopyEffect copy() {
        return new UnboundFlourishingCopyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (player != null && controller != null) {
            Object needObject = game.getState().getValue(source.getSourceId() + UnboundFlourishing.needPrefix);

            // copy ability
            if (needObject instanceof StackAbility) {
                StackAbility stackAbility = (StackAbility) needObject;
                stackAbility.createCopyOnStack(game, source, source.getControllerId(), true);
                return true;
            }

            // copy spell
            if (needObject instanceof Spell) {
                Spell spell = (Spell) needObject;
                spell.createCopyOnStack(game, source, source.getControllerId(), true);
                return true;
            }
        }
        return false;
    }
}
