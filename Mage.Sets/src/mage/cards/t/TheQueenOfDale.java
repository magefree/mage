package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.keyword.RecruitEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author muz
 */
public final class TheQueenOfDale extends CardImpl {

    public TheQueenOfDale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever an opponent casts their first noncreature spell each turn, you recruit.
        this.addAbility(new TheQueenOfDaleTriggeredAbility());
    }

    private TheQueenOfDale(final TheQueenOfDale card) {
        super(card);
    }

    @Override
    public TheQueenOfDale copy() {
        return new TheQueenOfDale(this);
    }
}

class TheQueenOfDaleTriggeredAbility extends TriggeredAbilityImpl {

    public TheQueenOfDaleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RecruitEffect());
    }

    private TheQueenOfDaleTriggeredAbility(final TheQueenOfDaleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheQueenOfDaleTriggeredAbility copy() {
        return new TheQueenOfDaleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(getControllerId());
        Spell spell = game.getSpell(event.getTargetId());
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (controller != null && spell != null && watcher != null && !spell.isCreature(game) && controller.hasOpponent(spell.getControllerId(), game)) {
            int nonCreatureSpells = 0;
            for (Spell spellCastThisTurn : watcher.getSpellsCastThisTurn(spell.getControllerId())) {
                if (!spellCastThisTurn.isCreature(game) && ++nonCreatureSpells > 1) {
                    break;
                }
            }
            if (nonCreatureSpells == 1) {
                getEffects().setTargetPointer(new FixedTarget(spell.getControllerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts their first noncreature spell each turn, you recruit.";
    }
}
