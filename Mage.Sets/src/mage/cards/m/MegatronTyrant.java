package mage.cards.m;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.dynamicvalue.common.OpponentsLostLifeCount;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.abilities.hint.StaticHint;
import mage.abilities.keyword.LivingMetalAbility;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.abilities.triggers.BeginningOfPostcombatMainTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class MegatronTyrant extends TransformingDoubleFacedCard {

    public MegatronTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.ROBOT}, "{3}{R}{W}{B}",
                "Megatron, Destructive Force",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.VEHICLE}, "RWB"
        );

        // Megatron, Tyrant
        this.getLeftHalfCard().setPT(7, 5);

        // More Than Meets the Eye {1}{R}{W}{B}
        this.getLeftHalfCard().addAbility(new MoreThanMeetsTheEyeAbility(this, "{1}{R}{W}{B}"));

        // Your opponents can't cast spells during combat.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new MegatronTyrantCantCastSpellsEffect()));

        // At the beginning of your postcombat main phase, you may convert Megatron. If you do, add {C} for each 1 life your opponents have lost this turn.
        TriggeredAbility trigger = new BeginningOfPostcombatMainTriggeredAbility(
                TargetController.YOU, new TransformSourceEffect().setText("convert {this}"),
                true
        );
        trigger.addEffect(
                new DynamicManaEffect(
                        Mana.ColorlessMana(1),
                        OpponentsLostLifeCount.instance,
                        "add {C} for each 1 life your opponents have lost this turn"
                ).concatBy("If you do,")
        );
        this.getLeftHalfCard().addAbility(trigger);

        // Megatron, Destructive Force
        this.getRightHalfCard().setPT(4, 5);

        // Living Metal
        this.getRightHalfCard().addAbility(new LivingMetalAbility());

        // Whenever Megatron attacks, you may sacrifice another artifact. When you do, Megatron deals damage equal to the sacrificed artifact's mana value to target creature. If excess damage would be dealt to that creature this way, instead that damage is dealt to that creature's controller and you convert Megatron.
        this.getRightHalfCard().addAbility(new AttacksTriggeredAbility(new MegatronDestructiveForceEffect()));
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

class MegatronDestructiveForceEffect extends OneShotEffect {

    MegatronDestructiveForceEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice another artifact. When you do, {this} deals damage equal to the sacrificed artifact's mana value " +
                "to target creature. If excess damage would be dealt to that creature this way, instead that damage is dealt to that creature's controller " +
                "and you convert {this}.";
    }

    private MegatronDestructiveForceEffect(final MegatronDestructiveForceEffect effect) {
        super(effect);
    }

    @Override
    public MegatronDestructiveForceEffect copy() {
        return new MegatronDestructiveForceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetSacrifice target = new TargetSacrifice(
                0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT
        );
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }

        int manaValue = Math.max(permanent.getManaValue(), 0);
        if (!permanent.sacrifice(source, game)) {
            return false;
        }

        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new MegatronDestructiveForceReflexiveEffect(manaValue), false
        );
        ability.addHint(new StaticHint("Sacrificed artifact mana value: " + manaValue));
        ability.addTarget(new TargetCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class MegatronDestructiveForceReflexiveEffect extends OneShotEffect {

    private final int value;

    MegatronDestructiveForceReflexiveEffect(int value) {
        super(Outcome.Damage);
        staticText = "{this} deals damage equal to the sacrificed artifact's mana value to target " +
                "creature. If excess damage would be dealt to that creature this way, instead that damage " +
                "is dealt to that creature's controller and you convert {this}.";
        this.value = value;
    }

    private MegatronDestructiveForceReflexiveEffect(final MegatronDestructiveForceReflexiveEffect effect) {
        super(effect);
        this.value = effect.value;
    }

    @Override
    public MegatronDestructiveForceReflexiveEffect copy() {
        return new MegatronDestructiveForceReflexiveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (value < 1) {
            return false;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int excess = permanent.damageWithExcess(value, source, game);
        if (excess < 1) {
            return true;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null) {
            player.damage(excess, source, game);
        }
        new TransformSourceEffect().apply(game, source);
        return true;
    }
}
