package mage.cards.s;

import mage.MageObject;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.AdventureCardSpell;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.SpellStack;
import mage.game.stack.StackObject;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author weirddan455
 */
public final class SilverScrutiny extends CardImpl {

    public SilverScrutiny(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // You may cast Silver Scrutiny as though it had flash if X is 3 or less.
        // Draw X cards.
        this.replaceSpellAbility(new SilverScrutinySpellAbility(this));
    }

    private SilverScrutiny(final SilverScrutiny card) {
        super(card);
    }

    @Override
    public SilverScrutiny copy() {
        return new SilverScrutiny(this);
    }
}

class SilverScrutinySpellAbility extends SpellAbility {

    public SilverScrutinySpellAbility(Card card) {
        super(card.getManaCost(), card.getName());
        this.addEffect(new InfoEffect("You mast cast {this} as though it had flash if X is 3 or less.<br>"));
        this.addEffect(new DrawCardSourceControllerEffect(ManacostVariableValue.REGULAR));
        this.addWatcher(new SilverScrutinyWatcher());
    }

    private SilverScrutinySpellAbility(final SilverScrutinySpellAbility ability) {
        super(ability);
    }

    @Override
    public SilverScrutinySpellAbility copy() {
        return new SilverScrutinySpellAbility(this);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        SilverScrutinyWatcher watcher = game.getState().getWatcher(SilverScrutinyWatcher.class);
        if (watcher != null && watcher.spellCastAsInstant(sourceId)) {
            for (Object cost : manaCostsToPay) {
                if (cost instanceof VariableManaCost) {
                    ((VariableManaCost) cost).setMaxX(3);
                }
            }
        }
        return super.activate(game, noMana);
    }
}

class SilverScrutinyWatcher extends Watcher {

    private final Set<UUID> spellsCast = new HashSet<>();

    public SilverScrutinyWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL) {
            Spell spell = game.getSpell(event.getTargetId());
            if (spell != null) {
                if (castAsSorcery(spell, game)) {
                    spellsCast.remove(spell.getSourceId());
                } else {
                    spellsCast.add(spell.getSourceId());
                }
            }
        }
    }

    // Copied from SpellAbility.spellCanBeActivatedRegularlyNow (changed to check spell on stack).
    private boolean castAsSorcery(Spell spell, Game game) {
        SpellAbility spellAbility = spell.getSpellAbility();
        if (spellAbility == null) {
            return false;
        }
        MageObject object = game.getObject(spellAbility.getSourceId());
        if (object == null) {
            return false;
        }

        // forced to cast (can be part id or main id)
        Set<UUID> idsToCheck = new HashSet<>();
        idsToCheck.add(object.getId());
        if (object instanceof Card && !(object instanceof AdventureCardSpell)) {
            idsToCheck.add(((Card) object).getMainCard().getId());
        }
        for (UUID idToCheck : idsToCheck) {
            if (game.getState().getValue("PlayFromNotOwnHandZone" + idToCheck) != null) {
                return (Boolean) game.getState().getValue("PlayFromNotOwnHandZone" + idToCheck);  // card like Chandra, Torch of Defiance +1 loyal ability)
            }
        }

        return null != game.getContinuousEffects().asThough(spellAbility.getSourceId(), AsThoughEffectType.CAST_AS_INSTANT, spellAbility, spell.getControllerId(), game) // check this first to allow Offering in main phase
                || object.isInstant(game)
                || object.hasAbility(FlashAbility.getInstance(), game)
                // Spell is already on the stack when this is called so this replaces game.canPlaySorcery
                || (game.isMainPhase() && game.isActivePlayer(spell.getControllerId()) && onlySpellOnStack(spell, game));
    }

    private boolean onlySpellOnStack(Spell spell, Game game) {
        SpellStack stack = game.getStack();
        if (stack.size() == 1) {
            StackObject stackObject = stack.peek();
            return stackObject != null && stackObject.getId().equals(spell.getId());
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        spellsCast.clear();
    }

    public boolean spellCastAsInstant(UUID sourceId) {
        return spellsCast.contains(sourceId);
    }
}
