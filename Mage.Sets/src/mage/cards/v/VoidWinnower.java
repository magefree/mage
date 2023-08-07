package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VoidWinnower extends CardImpl {

    public VoidWinnower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{9}");
        this.subtype.add(SubType.ELDRAZI);

        this.power = new MageInt(11);
        this.toughness = new MageInt(9);

        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VoidWinnowerCantCastEffect()));

        // Your opponents can't block with creatures with even converted mana costs.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VoidWinnowerCantBlockEffect()));
    }

    private VoidWinnower(final VoidWinnower card) {
        super(card);
    }

    @Override
    public VoidWinnower copy() {
        return new VoidWinnower(this);
    }
}

class VoidWinnowerCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public VoidWinnowerCantCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Your opponents can't cast spells with even mana values. <i>(Zero is even.)</i>";
    }

    public VoidWinnowerCantCastEffect(final VoidWinnowerCantCastEffect effect) {
        super(effect);
    }

    @Override
    public VoidWinnowerCantCastEffect copy() {
        return new VoidWinnowerCantCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast spells with even mana values (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                // the low bit will always be set on an odd number.
                return (spell.getManaValue() & 1) == 0;
            }
        }
        return false;
    }
}

class VoidWinnowerCantBlockEffect extends RestrictionEffect {

    public VoidWinnowerCantBlockEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Your opponents can't block with creatures with even mana values";
    }

    public VoidWinnowerCantBlockEffect(final VoidWinnowerCantBlockEffect effect) {
        super(effect);
    }

    @Override
    public VoidWinnowerCantBlockEffect copy() {
        return new VoidWinnowerCantBlockEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())) {
            // the low bit will always be set on an odd number.
            return (permanent.getManaValue() & 1) == 0 && permanent.isCreature(game);
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }
}
