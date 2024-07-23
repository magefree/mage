package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackAbility;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Map;
import java.util.UUID;

/**
 * @author JayDi85
 */
public final class UnboundFlourishing extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a permanent spell with a mana cost that contains {X}");

    static {
        filter.add(PermanentPredicate.instance);
        filter.add(UnboundFlourishingSpellContainsXPredicate.instance);
    }

    public UnboundFlourishing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        // Whenever you cast a permanent spell with a mana cost that contains {X}, double the value of X.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UnboundFlourishingDoubleXEffect(), filter, false, SetTargetPointer.SPELL));

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

enum UnboundFlourishingSpellContainsXPredicate implements Predicate<StackObject> {
    instance;

    @Override
    public boolean apply(StackObject input, Game game) {
        if (input instanceof Spell) {
            return ((Spell) input).getSpellAbility().getManaCostsToPay().containsX();
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Contains {X}";
    }
}

class UnboundFlourishingDoubleXEffect extends OneShotEffect {

    UnboundFlourishingDoubleXEffect() {
        super(Outcome.Benefit);
        this.staticText = ", double the value of X";
    }

    private UnboundFlourishingDoubleXEffect(final UnboundFlourishingDoubleXEffect effect) {
        super(effect);
    }

    @Override
    public UnboundFlourishingDoubleXEffect copy() {
        return new UnboundFlourishingDoubleXEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && controller != null) {
            Spell needObject = game.getSpell(getTargetPointer().getFirst(game, source));
            if (needObject != null) {
                Map<String, Object> tagsMap = CardUtil.getSourceCostsTagsMap(game, needObject.getSpellAbility());
                if (tagsMap.containsKey("X")) {
                    tagsMap.put("X", ((int) tagsMap.get("X")) * 2);
                }
            }
        }
        return false;
    }
}

class UnboundFlourishingCopyAbility extends TriggeredAbilityImpl {

    UnboundFlourishingCopyAbility() {
        super(Zone.BATTLEFIELD, new CopyStackObjectEffect("that spell or ability"), false);
        setTriggerPhrase("Whenever you cast an instant or sorcery spell or activate an ability, " +
                "if that spell's mana cost or that ability's activation cost contains {X}");
    }

    private UnboundFlourishingCopyAbility(final UnboundFlourishingCopyAbility ability) {
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
            if (stackAbility != null && !stackAbility.getStackAbility().isManaActivatedAbility()) {
                if (stackAbility.getManaCostsToPay().containsX()) {
                    this.getEffects().setValue("stackObject", stackAbility);
                    return true;
                }
            }
        }

        // spell
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.isInstantOrSorcery(game)) {
                if (spell.getSpellAbility().getManaCostsToPay().containsX()) {
                    this.getEffects().setValue("stackObject", spell);
                    return true;
                }
            }
        }
        return false;
    }
}
