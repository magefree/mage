package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.FirstXSpellCastThisTurnWatcher;

/**
 *
 * @author muz
 */
public final class OwlinSpiralmancer extends CardImpl {

    public OwlinSpiralmancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast your first spell with {X} in its mana cost each turn, you may copy it. You may choose new targets for the copy.
        this.addAbility(new OwlinSpiralmancerTriggeredAbility(), new FirstXSpellCastThisTurnWatcher());
    }

    private OwlinSpiralmancer(final OwlinSpiralmancer card) {
        super(card);
    }

    @Override
    public OwlinSpiralmancer copy() {
        return new OwlinSpiralmancer(this);
    }
}

class OwlinSpiralmancerTriggeredAbility extends TriggeredAbilityImpl {

    OwlinSpiralmancerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyStackObjectEffect(), true);
    }

    private OwlinSpiralmancerTriggeredAbility(final OwlinSpiralmancerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null) {
            return false;
        }
        FirstXSpellCastThisTurnWatcher watcher = game.getState().getWatcher(FirstXSpellCastThisTurnWatcher.class);
        if (watcher == null || !spell.getId().equals(watcher.getFirstXSpellId(getControllerId()))) {
            return false;
        }
        getAllEffects().setTargetPointer(new FixedTarget(spell.getId(), game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you cast your first spell with {X} in its mana cost each turn, you may copy it. You may choose new targets for the copy.";
    }

    @Override
    public OwlinSpiralmancerTriggeredAbility copy() {
        return new OwlinSpiralmancerTriggeredAbility(this);
    }
}
