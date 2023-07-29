package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfPostCombatMainTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MegatronTyrant extends CardImpl {

    public MegatronTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.m.MegatronDestructiveForce.class;

        // More Than Meets the Eye {1}{R}{W}{B}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{1}{R}{W}{B}"));

        // Your opponents can't cast spells during combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MegatronTyrantCantCastSpellsEffect()));

        // At the beginning of your postcombat main phase, you may convert Megatron. If you do, add {C} for each 1 life your opponents have lost this turn.
        TriggeredAbility trigger = new BeginningOfPostCombatMainTriggeredAbility(
            new TransformSourceEffect().setText("convert {this}"),
            TargetController.YOU,
            true
        );
        trigger.addEffect(
            new DynamicManaEffect(
                Mana.ColorlessMana(1),
                OpponentsLostLifeCount.instance,
                "add {C} for each 1 life your opponents have lost this turn"
            ).concatBy("If you do, ")
        );

        this.addAbility(trigger);
    }

    private MegatronTyrant(final MegatronTyrant card) {
        super(card);
    }

    @Override
    public MegatronTyrant copy() {
        return new MegatronTyrant(this);
    }
}

class MegatronTyrantCantCastSpellsEffect extends ContinuousRuleModifyingEffectImpl {

    MegatronTyrantCantCastSpellsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Your opponents can't cast spells during combat.";
    }

    private MegatronTyrantCantCastSpellsEffect(final MegatronTyrantCantCastSpellsEffect effect) {
        super(effect);
    }

    @Override
    public MegatronTyrantCantCastSpellsEffect copy() {
        return new MegatronTyrantCantCastSpellsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't cast spells during combat (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getOpponents(source.getControllerId()).contains(event.getPlayerId())
            && game.getTurnPhaseType() == TurnPhase.COMBAT;
    }

}