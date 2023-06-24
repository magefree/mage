package mage.cards.b;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BriselaVoiceOfNightmares extends MeldCard {
    public BriselaVoiceOfNightmares(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI, SubType.ANGEL);
        this.power = new MageInt(9);
        this.toughness = new MageInt(10);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Your opponents can't cast spells with converted mana cost 3 or less.
        this.addAbility(new SimpleStaticAbility(new BriselaVoiceOfNightmaresCantCastEffect()));
    }

    private BriselaVoiceOfNightmares(final BriselaVoiceOfNightmares card) {
        super(card);
    }

    @Override
    public BriselaVoiceOfNightmares copy() {
        return new BriselaVoiceOfNightmares(this);
    }
}

class BriselaVoiceOfNightmaresCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public BriselaVoiceOfNightmaresCantCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Your opponents can't cast spells with mana value 3 or less";
    }

    public BriselaVoiceOfNightmaresCantCastEffect(final BriselaVoiceOfNightmaresCantCastEffect effect) {
        super(effect);
    }

    @Override
    public BriselaVoiceOfNightmaresCantCastEffect copy() {
        return new BriselaVoiceOfNightmaresCantCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast spells with mana value 3 or less (" + mageObject.getIdName() + ").";
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
                return spell.getManaValue() < 4;
            }
        }
        return false;
    }
}
