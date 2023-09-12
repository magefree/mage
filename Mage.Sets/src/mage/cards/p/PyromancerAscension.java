package mage.cards.p;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class PyromancerAscension extends CardImpl {

    public PyromancerAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // Whenever you cast an instant or sorcery spell that has the same name as a card in your graveyard, you may put a quest counter on Pyromancer Ascension.
        this.addAbility(new PyromancerAscensionQuestTriggeredAbility());

        // Whenever you cast an instant or sorcery spell while Pyromancer Ascension has two or more quest counters on it, you may copy that spell. You may choose new targets for the copy.
        this.addAbility(new PyromancerAscensionCopyTriggeredAbility());
    }

    private PyromancerAscension(final PyromancerAscension card) {
        super(card);
    }

    @Override
    public PyromancerAscension copy() {
        return new PyromancerAscension(this);
    }

}

class PyromancerAscensionQuestTriggeredAbility extends TriggeredAbilityImpl {

    PyromancerAscensionQuestTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance(), true), true);
    }

    private PyromancerAscensionQuestTriggeredAbility(final PyromancerAscensionQuestTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PyromancerAscensionQuestTriggeredAbility copy() {
        return new PyromancerAscensionQuestTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (isControlledInstantOrSorcery(spell, game)) {
                Card sourceCard = game.getCard(spell.getSourceId());
                if (sourceCard != null) {
                    for (UUID uuid : game.getPlayer(this.getControllerId()).getGraveyard()) {
                        if (!uuid.equals(sourceCard.getId())) {
                            Card card = game.getCard(uuid);
                            if (CardUtil.haveSameNames(card, sourceCard)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean isControlledInstantOrSorcery(Spell spell, Game game) {
        return spell != null &&
                spell.isControlledBy(this.getControllerId()) &&
                spell.isInstantOrSorcery(game);
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell that has the same name as a card in your graveyard, you may put a quest counter on {this}.";
    }
}

class PyromancerAscensionCopyTriggeredAbility extends TriggeredAbilityImpl {

    PyromancerAscensionCopyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyTargetSpellEffect(true), true);
    }

    private PyromancerAscensionCopyTriggeredAbility(final PyromancerAscensionCopyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PyromancerAscensionCopyTriggeredAbility copy() {
        return new PyromancerAscensionCopyTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (isControlledInstantOrSorcery(spell, game)) {
                Permanent permanent = game.getBattlefield().getPermanent(this.getSourceId());
                if (permanent != null && permanent.getCounters(game).getCount(CounterType.QUEST) >= 2) {
                    this.getEffects().get(0).setTargetPointer(new FixedTarget(spell.getId()));
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isControlledInstantOrSorcery(Spell spell, Game game) {
        return spell != null &&
                spell.isControlledBy(this.getControllerId()) &&
                spell.isInstantOrSorcery(game);
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell while {this} has two or more quest counters on it, you may copy that spell. You may choose new targets for the copy.";
    }
}
