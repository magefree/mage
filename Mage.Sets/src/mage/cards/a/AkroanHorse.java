package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AkroanHorse extends CardImpl {

    public AkroanHorse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Akroan Horse enters the battlefield, an opponent gains control of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AkroanHorseChangeControlEffect(), false
        ));

        // At the beginning of your upkeep, each opponent create a 1/1 white Soldier creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenAllEffect(new SoldierToken(), TargetController.OPPONENT),
                TargetController.YOU, false
        ));
    }

    private AkroanHorse(final AkroanHorse card) {
        super(card);
    }

    @Override
    public AkroanHorse copy() {
        return new AkroanHorse(this);
    }
}

class AkroanHorseChangeControlEffect extends OneShotEffect {

    public AkroanHorseChangeControlEffect() {
        super(Outcome.Benefit);
        this.staticText = "an opponent gains control of it";
    }

    private AkroanHorseChangeControlEffect(final AkroanHorseChangeControlEffect effect) {
        super(effect);
    }

    @Override
    public AkroanHorseChangeControlEffect copy() {
        return new AkroanHorseChangeControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target target = new TargetOpponent();
        target.setNotTarget(true);
        controller.chooseTarget(outcome, target, source, game);
        ContinuousEffect effect = new AkroanHorseGainControlEffect(Duration.Custom, target.getFirstTarget());
        effect.setTargetPointer(new FixedTarget(source.getSourceId(), game));
        game.addEffect(effect, source);
        return true;
    }
}

class AkroanHorseGainControlEffect extends ContinuousEffectImpl {

    private final UUID controller;

    public AkroanHorseGainControlEffect(Duration duration, UUID controller) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controller = controller;
        this.staticText = "Gain control of Akroan Horse";
    }

    public AkroanHorseGainControlEffect(final AkroanHorseGainControlEffect effect) {
        super(effect);
        this.controller = effect.controller;
    }

    @Override
    public AkroanHorseGainControlEffect copy() {
        return new AkroanHorseGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent;
        if (targetPointer == null) {
            permanent = game.getPermanent(source.getFirstTarget());
        } else {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
        }
        if (permanent == null) {
            return false;
        }
        return permanent.changeControllerId(controller, game, source);
    }
}
