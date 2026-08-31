package mage.cards.t;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class TheFrightfulFour extends CardImpl {

    public TheFrightfulFour(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever an opponent casts their first noncreature spell each turn, that player loses life equal to that spell's mana value.
        this.addAbility(new TheFrightfulFourTriggeredAbility());
    }

    private TheFrightfulFour(final TheFrightfulFour card) {
        super(card);
    }

    @Override
    public TheFrightfulFour copy() {
        return new TheFrightfulFour(this);
    }
}

class TheFrightfulFourTriggeredAbility extends TriggeredAbilityImpl {

    TheFrightfulFourTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TheFrightfulFourEffect());
    }

    private TheFrightfulFourTriggeredAbility(final TheFrightfulFourTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheFrightfulFourTriggeredAbility copy() {
        return new TheFrightfulFourTriggeredAbility(this);
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
        return "Whenever an opponent casts their first noncreature spell each turn, that player loses life equal to that spell's mana value.";
    }
}

class TheFrightfulFourEffect extends OneShotEffect {

    TheFrightfulFourEffect() {
        super(Outcome.LoseLife);
    }

    private TheFrightfulFourEffect(final TheFrightfulFourEffect effect) {
        super(effect);
    }

    @Override
    public TheFrightfulFourEffect copy() {
        return new TheFrightfulFourEffect(this);
    }

    @Override
    public boolean apply (Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent == null) {
            return false;
        }
        Spell spell = (Spell) getValue("spellCast");
        int mv = Optional
            .ofNullable(spell)
            .map(Spell::getManaValue)
            .orElse(0);
        opponent.loseLife(mv, game, source, false);
        return true;
    }
}
