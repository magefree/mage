package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LivingBreakthrough extends CardImpl {

    public LivingBreakthrough(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.subtype.add(SubType.MOONFOLK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlue(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you cast a spell, your opponents can't cast spells with the same mana value as that spell until your next turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(new LivingBreakthroughEffect(), false));
    }

    private LivingBreakthrough(final LivingBreakthrough card) {
        super(card);
    }

    @Override
    public LivingBreakthrough copy() {
        return new LivingBreakthrough(this);
    }
}

class LivingBreakthroughEffect extends ContinuousRuleModifyingEffectImpl {

    private int manaValue = -1;

    LivingBreakthroughEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Benefit);
        staticText = "your opponents can't cast spells with the same mana value as that spell until your next turn";
    }

    private LivingBreakthroughEffect(final LivingBreakthroughEffect effect) {
        super(effect);
        this.manaValue = effect.manaValue;
    }

    @Override
    public LivingBreakthroughEffect copy() {
        return new LivingBreakthroughEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Spell spell = (Spell) getValue("spellCast");
        if (spell != null) {
            this.manaValue = spell.getManaValue();
        }
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast spells with mana value " + manaValue
                    + " this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && spell.getManaValue() == this.manaValue;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
