package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.MyTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HumbleDefector extends CardImpl {

    public HumbleDefector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: Draw two cards. Target opponent gains control of Humble Defector. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new HumbleDefectorEffect(), new TapSourceCost(), MyTurnCondition.instance);
        ability.addTarget(new TargetOpponent());
        ability.addHint(MyTurnHint.instance);
        this.addAbility(ability);

    }

    private HumbleDefector(final HumbleDefector card) {
        super(card);
    }

    @Override
    public HumbleDefector copy() {
        return new HumbleDefector(this);
    }
}

class HumbleDefectorEffect extends OneShotEffect {

    public HumbleDefectorEffect() {
        super(Outcome.Discard);
        this.staticText = "Draw two cards. Target opponent gains control of {this}.";
    }

    public HumbleDefectorEffect(final HumbleDefectorEffect effect) {
        super(effect);
    }

    @Override
    public HumbleDefectorEffect copy() {
        return new HumbleDefectorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.drawCards(2, source, game);
        }
        Permanent humbleDefector = source.getSourcePermanentIfItStillExists(game);
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetOpponent != null && humbleDefector != null) {
            ContinuousEffect effect = new HumbleDefectorControlSourceEffect();
            game.addEffect(effect, source);
            game.informPlayers(humbleDefector.getName() + " is now controlled by " + targetOpponent.getLogName());
            return true;
        }
        return false;
    }
}

class HumbleDefectorControlSourceEffect extends ContinuousEffectImpl {

    public HumbleDefectorControlSourceEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
    }

    public HumbleDefectorControlSourceEffect(final HumbleDefectorControlSourceEffect effect) {
        super(effect);
    }

    @Override
    public HumbleDefectorControlSourceEffect copy() {
        return new HumbleDefectorControlSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(source.getFirstTarget());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && targetOpponent != null) {
            permanent.changeControllerId(targetOpponent.getId(), game, source);
        } else {
            // no valid target exists, effect can be discarded
            discard();
        }
        return true;
    }
}