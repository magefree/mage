package mage.cards.m;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MageHunter extends CardImpl {

    public MageHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever an opponent casts or copies an instant or sorcery spell, they lose 1 life.
        this.addAbility(new MageHunterTriggeredAbility());
    }

    private MageHunter(final MageHunter card) {
        super(card);
    }

    @Override
    public MageHunter copy() {
        return new MageHunter(this);
    }
}

class MageHunterTriggeredAbility extends TriggeredAbilityImpl {

    MageHunterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LoseLifeTargetEffect(1), false);
    }

    private MageHunterTriggeredAbility(final MageHunterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COPIED_STACKOBJECT
                || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null
                || !game.getOpponents(getControllerId()).contains(spell.getControllerId())
                || !spell.isInstantOrSorcery(game)) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(spell.getControllerId()));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts or copies an instant or sorcery spell, they lose 1 life.";
    }

    @Override
    public MageHunterTriggeredAbility copy() {
        return new MageHunterTriggeredAbility(this);
    }
}
