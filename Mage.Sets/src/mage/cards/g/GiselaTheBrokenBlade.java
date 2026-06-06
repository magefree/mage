package mage.cards.g;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.MeldCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.MeldEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.cards.b.BrunaTheFadingLight;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GiselaTheBrokenBlade extends MeldCard {

    private static final Condition condition = new MeldCondition("Bruna, the Fading Light");

    public GiselaTheBrokenBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, 
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ANGEL, SubType.HORROR}, "{2}{W}{W}",
                "Brisela, Voice of Nightmares",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.ANGEL}, "");

        this.meldsWithClazz = BrunaTheFadingLight.class;

        // Gisela, the Broken Blade
        this.getLeftHalfCard().setPT(4, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // First strike
        this.getLeftHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, if you both own and control Gisela, the Broken Blade and a creature named Bruna, the Fading Light, exile them, then meld them into Brisela, Voice of Nightmares.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(TargetController.YOU, new MeldEffect(
                "Bruna, the Fading Light", "Brisela, Voice of Nightmares"
        ).setText("exile them, then meld them into Brisela, Voice of Nightmares"), false, condition));
        
        // Brisela, Voice of Nightmares
        this.getRightHalfCard().setPT(9, 10);

        // Flying
        this.getRightHalfCard().addAbility(FlyingAbility.getInstance());

        // First strike
        this.getRightHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());

        // Your opponents can't cast spells with converted mana cost 3 or less.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new BriselaVoiceOfNightmaresCantCastEffect()));
    }

    private GiselaTheBrokenBlade(final GiselaTheBrokenBlade card) {
        super(card);
    }

    @Override
    public GiselaTheBrokenBlade copy() {
        return new GiselaTheBrokenBlade(this);
    }
}


class BriselaVoiceOfNightmaresCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    BriselaVoiceOfNightmaresCantCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Your opponents can't cast spells with mana value 3 or less";
    }

    private BriselaVoiceOfNightmaresCantCastEffect(final BriselaVoiceOfNightmaresCantCastEffect effect) {
        super(effect);
    }

    @Override
    public BriselaVoiceOfNightmaresCantCastEffect copy() {
        return new BriselaVoiceOfNightmaresCantCastEffect(this);
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
