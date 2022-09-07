package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.TargetOfOpponentsSpellOrAbilityTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class ParnesseTheSubtleBrush extends CardImpl {

    protected static final String SPELL_KEY = "castCopiedSpell";

    public ParnesseTheSubtleBrush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.VAMPIRE, SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you or a permanent you control becomes the target of a spell or ability an opponent controls,
        // counter that spell or ability unless that player pays 4 life.
        this.addAbility(new TargetOfOpponentsSpellOrAbilityTriggeredAbility(new CounterUnlessPaysEffect(new PayLifeCost(4).setText("4 life"))));

        // Whenever you copy a spell, up to one target opponent may also copy that spell.
        // They may choose new targets for that copy.
        this.addAbility(new ParnesseTheSubtleBrushCopySpellTriggeredAbility());
    }

    private ParnesseTheSubtleBrush(final ParnesseTheSubtleBrush card) {
        super(card);
    }

    @Override
    public ParnesseTheSubtleBrush copy() {
        return new ParnesseTheSubtleBrush(this);
    }
}

class ParnesseTheSubtleBrushCopySpellOpponentEffect extends OneShotEffect {

    ParnesseTheSubtleBrushCopySpellOpponentEffect() {
        super(Outcome.Detriment);
        this.staticText = "up to one target opponent may also copy that spell. " +
                          "They may choose new targets for that copy";
    }

    ParnesseTheSubtleBrushCopySpellOpponentEffect(final ParnesseTheSubtleBrushCopySpellOpponentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        Object object = getValue(ParnesseTheSubtleBrush.SPELL_KEY);
        if (opponent == null || !(object instanceof Spell)) {
            return false;
        }
        Spell spellToCopy = (Spell) object;
        spellToCopy.createCopyOnStack(game, source, opponent.getId(), true);
        return true;
    }

    @Override
    public Effect copy() {
        return new ParnesseTheSubtleBrushCopySpellOpponentEffect(this);
    }
}

class ParnesseTheSubtleBrushCopySpellTriggeredAbility extends TriggeredAbilityImpl {

    ParnesseTheSubtleBrushCopySpellTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ParnesseTheSubtleBrushCopySpellOpponentEffect(), true);
        this.getTargets().add(new TargetOpponent(0, 1, false));
    }

    private ParnesseTheSubtleBrushCopySpellTriggeredAbility(final ParnesseTheSubtleBrushCopySpellTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COPIED_STACKOBJECT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getSpell(event.getTargetId());
        if (spell == null || !spell.isControlledBy(this.getControllerId())) {
            return false;
        }
        getEffects().setValue(ParnesseTheSubtleBrush.SPELL_KEY, spell);
        return true;
    }

    @Override
    public TriggeredAbility copy() {
        return new ParnesseTheSubtleBrushCopySpellTriggeredAbility(this);
    }
}