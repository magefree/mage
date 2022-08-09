package mage.cards.c;

import java.util.UUID;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.constants.Outcome;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author weirddan455
 */
public final class CurseOfShakenFaith extends CardImpl {

    public CurseOfShakenFaith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.subtype.add(SubType.AURA);
        this.subtype.add(SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted player casts a spell other than the first spell they cast each turn or copies a spell, Curse of Shaken Faith deals 2 damage to them.
        this.addAbility(new CurseOfShakenFaithTriggeredAbility());
    }

    private CurseOfShakenFaith(final CurseOfShakenFaith card) {
        super(card);
    }

    @Override
    public CurseOfShakenFaith copy() {
        return new CurseOfShakenFaith(this);
    }
}

class CurseOfShakenFaithTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfShakenFaithTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CurseOfShakenFaithEffect());
        this.addWatcher(new SpellsCastWatcher());
        setTriggerPhrase("Whenever enchanted player casts a spell other than the first spell they cast each turn or copies a spell, ");
    }

    private CurseOfShakenFaithTriggeredAbility(final CurseOfShakenFaithTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CurseOfShakenFaithTriggeredAbility copy() {
        return new CurseOfShakenFaithTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COPIED_STACKOBJECT
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(sourceId);
        if (enchantment != null) {
            UUID enchantedPlayerId = enchantment.getAttachedTo();
            Spell spell = game.getSpell(event.getTargetId());
            if (spell != null && spell.isControlledBy(enchantedPlayerId)) {
                if (event.getType() == GameEvent.EventType.COPIED_STACKOBJECT) {
                    return true;
                }
                SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
                if (watcher != null) {
                    return watcher.getSpellsCastThisTurn(enchantedPlayerId).size() > 1;
                }
            }
        }
        return false;
    }
}

class CurseOfShakenFaithEffect extends OneShotEffect {

    public CurseOfShakenFaithEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to them";
    }

    private CurseOfShakenFaithEffect(final CurseOfShakenFaithEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfShakenFaithEffect copy() {
        return new CurseOfShakenFaithEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (enchantment != null) {
            Player enchantedPlayer = game.getPlayer(enchantment.getAttachedTo());
            if (enchantedPlayer != null) {
                enchantedPlayer.damage(2, source.getSourceId(), source, game);
                return true;
            }
        }
        return false;
    }
}
