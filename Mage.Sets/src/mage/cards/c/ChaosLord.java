package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.ControlsPermanentsComparedToOpponentsCondition;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public final class ChaosLord extends CardImpl {

    public ChaosLord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // At the beginning of your upkeep, target opponent gains control of Chaos Lord if the number of permanents is even.
        Ability ability = new ChaosLordTriggeredAbility();
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Chaos Lord can attack as though it had haste unless it entered the battlefield this turn.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new ChaosLordEffect()));

    }

    private ChaosLord(final ChaosLord card) {
        super(card);
    }

    @Override
    public ChaosLord copy() {
        return new ChaosLord(this);
    }
}

class ChaosLordTriggeredAbility extends BeginningOfUpkeepTriggeredAbility {

    ChaosLordTriggeredAbility() {
        super(Zone.BATTLEFIELD,
                new GainControlSourceEffect(),
                TargetController.YOU,
                false);
    }

    private ChaosLordTriggeredAbility(final ChaosLordTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ChaosLordTriggeredAbility copy() {
        return new ChaosLordTriggeredAbility(this);
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        Condition condition = new ControlsPermanentsComparedToOpponentsCondition(
                ComparisonType.EQUAL_TO,
                new FilterPermanent());
        Player controller = game.getPlayer(controllerId);
        if (controller != null
                && condition.apply(game, this)) {
            return super.checkInterveningIfClause(game);
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, target opponent gains control of {this} if the number of permanents is even.";
    }

}

class GainControlSourceEffect extends ContinuousEffectImpl {

    GainControlSourceEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        staticText = "target opponent gains control of {this}";
    }

    private GainControlSourceEffect(final GainControlSourceEffect effect) {
        super(effect);
    }

    @Override
    public GainControlSourceEffect copy() {
        return new GainControlSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            return permanent.changeControllerId(source.getFirstTarget(), game, source);
        } else {
            discard();
        }
        return false;
    }
}

class ChaosLordEffect extends AsThoughEffectImpl {

    ChaosLordEffect() {
        super(AsThoughEffectType.ATTACK_AS_HASTE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Chaos Lord can attack as though it had haste unless it entered the battlefield this turn";
    }

    private ChaosLordEffect(final ChaosLordEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ChaosLordEffect copy() {
        return new ChaosLordEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Permanent chaosLord = game.getPermanent(objectId);
        return chaosLord != null
                && objectId == source.getSourceId()
                && chaosLord.getTurnsOnBattlefield() > 0;
    }
}
