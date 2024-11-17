
package mage.cards.m;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainsChoiceOfAbilitiesEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class MultiformWonder extends CardImpl {

    public MultiformWonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Multiform Wonder enters the battlefield, you get {E}{E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(3), false));

        // Pay {E}: Multiform Wonder gains your choice of flying, vigilance, or lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainsChoiceOfAbilitiesEffect(GainsChoiceOfAbilitiesEffect.TargetType.Source,
                FlyingAbility.getInstance(), VigilanceAbility.getInstance(), LifelinkAbility.getInstance()), new PayEnergyCost(1)));

        // Pay {E}: Multiform Wonder gets +2/-2 or -2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new MultiformWonder2Effect(), new PayEnergyCost(1)));
    }

    private MultiformWonder(final MultiformWonder card) {
        super(card);
    }

    @Override
    public MultiformWonder copy() {
        return new MultiformWonder(this);
    }
}

class MultiformWonder2Effect extends OneShotEffect {
    public MultiformWonder2Effect() {
        super(Outcome.BoostCreature);
        this.staticText = "{this} gets +2/-2 or -2/+2 until end of turn";
    }

    private MultiformWonder2Effect(final MultiformWonder2Effect effect) {
        super(effect);
    }

    @Override
    public MultiformWonder2Effect copy() {
        return new MultiformWonder2Effect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            int power = 2;
            int toughness = -2;
            String message = "Should " + sourceObject.getLogName() + " get -2/+2 instead of +2/-2?";
            if (controller.chooseUse(Outcome.Neutral, message, source, game)) {
                power *= -1;
                toughness *= -1;
            }
            game.addEffect(new BoostSourceEffect(power, toughness, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}
