package mage.cards.f;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacesCard;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlamescrollCelebrant extends ModalDoubleFacesCard {

    public FlamescrollCelebrant(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SHAMAN}, "{1}{R}",
                "Revel in Silence",
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{W}{W}"
        );


        // 1.
        // Flamescroll Celebrant
        // Creature - Human Shaman
        this.getLeftHalfCard().setPT(2, 1);

        // Whenever an opponent activates an ability that isn't a mana ability, Flamescroll Celebrant deals 1 damage to that player.
        this.getLeftHalfCard().addAbility(new FlamescrollCelebrantTriggeredAbility());

        // {1}{R}: Flamescroll Celebrant gets +2/+0 until end of turn.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{1}{R}")
        ));

        // 2.
        // Revel in Silence
        // Instant
        // Your opponents can't cast spells or activate planeswalkers' loyalty abilities this turn.
        this.getRightHalfCard().getSpellAbility().addEffect(new RevelInSilenceEffect());

        // Exile Revel in Silence.
        this.getRightHalfCard().getSpellAbility().addEffect(new ExileSpellEffect().concatBy("<br>"));
    }

    private FlamescrollCelebrant(final FlamescrollCelebrant card) {
        super(card);
    }

    @Override
    public FlamescrollCelebrant copy() {
        return new FlamescrollCelebrant(this);
    }
}

class FlamescrollCelebrantTriggeredAbility extends TriggeredAbilityImpl {

    FlamescrollCelebrantTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(StaticValue.get(1), true, "that player", true));
    }

    private FlamescrollCelebrantTriggeredAbility(final FlamescrollCelebrantTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FlamescrollCelebrantTriggeredAbility copy() {
        return new FlamescrollCelebrantTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(event.getPlayerId()).contains(getControllerId())) {
            return false;
        }
        StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
        if (stackAbility.getStackAbility() instanceof ActivatedManaAbilityImpl) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent activates an ability that isn't a mana ability, " +
                "{this} deals 1 damage to that player.";
    }
}

class RevelInSilenceEffect extends ContinuousRuleModifyingEffectImpl {

    RevelInSilenceEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Your opponents can't cast spells or activate planeswalkers' loyalty abilities this turn.";
    }

    private RevelInSilenceEffect(final RevelInSilenceEffect effect) {
        super(effect);
    }

    @Override
    public RevelInSilenceEffect copy() {
        return new RevelInSilenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        MageObject mageObject = game.getObject(source);
        if (activePlayer == null || mageObject == null) {
            return null;
        }
        return "You can't cast spells or activate planeswalkers' loyalty abilities this turn (" + mageObject.getLogName() + ')';
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL
                || event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            return false;
        }
        switch (event.getType()) {
            case CAST_SPELL:
                return true;
            case ACTIVATE_ABILITY:
                Ability ability = game.getAbility(event.getTargetId(), event.getSourceId()).orElse(null);
                if (!(ability instanceof LoyaltyAbility)) {
                    return false;
                }
                Permanent permanent = game.getPermanent(event.getSourceId());
                return permanent != null && permanent.isPlaneswalker(game);
        }
        return false;
    }
}
