
package mage.cards.m;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

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
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MultiformWonderEffect(), new PayEnergyCost(1)));

        // Pay {E}: Multiform Wonder gets +2/-2 or -2/+2 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MultiformWonder2Effect(), new PayEnergyCost(1)));
    }

    private MultiformWonder(final MultiformWonder card) {
        super(card);
    }

    @Override
    public MultiformWonder copy() {
        return new MultiformWonder(this);
    }
}

class MultiformWonderEffect extends OneShotEffect {

    private static final Set<String> choices = new LinkedHashSet<>();

    static {
        choices.add("Flying");
        choices.add("Vigilance");
        choices.add("Lifelink");
    }

    public MultiformWonderEffect() {
        super(Outcome.AddAbility);
        staticText = "{this} gains your choice of flying, vigilance, or lifelink until end of turn";
    }

    public MultiformWonderEffect(final MultiformWonderEffect effect) {
        super(effect);
    }

    @Override
    public MultiformWonderEffect copy() {
        return new MultiformWonderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose ability to add");
            choice.setChoices(choices);
            if (!controller.choose(outcome, choice, game)) {
                return false;
            }

            Ability gainedAbility;
            String chosen = choice.getChoice();
            switch (chosen) {
                case "Flying":
                    gainedAbility = FlyingAbility.getInstance();
                    break;
                case "Vigilance":
                    gainedAbility = VigilanceAbility.getInstance();
                    break;
                default:
                    gainedAbility = LifelinkAbility.getInstance();
                    break;
            }

            game.addEffect(new GainAbilitySourceEffect(gainedAbility, Duration.EndOfTurn), source);
            return true;
        }
        return false;
    }
}

class MultiformWonder2Effect extends ContinuousEffectImpl {

    private int power;
    private int toughness;

    public MultiformWonder2Effect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = 2;
        this.toughness = -2;
        this.staticText = "{this} gets +2/-2 or -2/+2 until end of turn";
    }

    public MultiformWonder2Effect(final MultiformWonder2Effect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public MultiformWonder2Effect copy() {
        return new MultiformWonder2Effect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            String message = "Should " + sourceObject.getLogName() + " get -2/+2 instead of +2/-2?";
            if (controller.chooseUse(Outcome.Neutral, message, source, game)) {
                this.power *= -1;
                this.toughness *= -1;
            }
        }

    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null) {
            sourceObject.addPower(power);
            sourceObject.addToughness(toughness);
            return true;
        }
        return false;
    }
}
