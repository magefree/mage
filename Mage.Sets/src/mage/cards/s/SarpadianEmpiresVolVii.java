
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.CamaridToken;
import mage.game.permanent.token.CitizenToken;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.SaprolingToken;
import mage.game.permanent.token.ThrullToken;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LoneFox
 */
public final class SarpadianEmpiresVolVii extends CardImpl {

    public SarpadianEmpiresVolVii(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // As Sarpadian Empires, Vol. VII enters the battlefield, choose white Citizen, blue Camarid, black Thrull, red Goblin, or green Saproling.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseTokenEffect()));
        // {3}, {T}: Create a 1/1 creature token of the chosen color and type.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateSelectedTokenEffect(), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public SarpadianEmpiresVolVii(final SarpadianEmpiresVolVii card) {
        super(card);
    }

    @Override
    public SarpadianEmpiresVolVii copy() {
        return new SarpadianEmpiresVolVii(this);
    }
}

class ChooseTokenEffect extends OneShotEffect {

    public ChooseTokenEffect() {
        super(Outcome.Neutral);
        this.staticText = "choose white Citizen, blue Camarid, black Thrull, red Goblin, or green Saproling";
    }

    public ChooseTokenEffect(final ChooseTokenEffect effect) {
        super(effect);
    }

    @Override
    public ChooseTokenEffect copy() {
        return new ChooseTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            ChoiceImpl choices = new ChoiceImpl(true);
            choices.setMessage("Choose token type");
            choices.getChoices().add("White Citizen");
            choices.getChoices().add("Blue Camarid");
            choices.getChoices().add("Black Thrull");
            choices.getChoices().add("Red Goblin");
            choices.getChoices().add("Green Saproling");
            if (controller.choose(Outcome.Neutral, choices, game)) {
                game.informPlayers(sourceObject.getLogName() + ": chosen token type is " + choices.getChoice());
                game.getState().setValue(source.getSourceId().toString() + "_SarpadianEmpiresVolVii", choices.getChoice());
                return true;
            }
        }
        return false;
    }
}

class CreateSelectedTokenEffect extends OneShotEffect {

    public CreateSelectedTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a 1/1 creature token of the chosen color and type";
    }

    public CreateSelectedTokenEffect(final CreateSelectedTokenEffect effect) {
        super(effect);
    }

    @Override
    public CreateSelectedTokenEffect copy() {
        return new CreateSelectedTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String tokenType = game.getState().getValue(source.getSourceId().toString() + "_SarpadianEmpiresVolVii").toString();
        Token token;
        switch (tokenType) {
            case "White Citizen":
                token = new CitizenToken();
                break;
            case "Blue Camarid":
                token = new CamaridToken();
                break;
            case "Black Thrull":
                token = new ThrullToken();
                break;
            case "Red Goblin":
                token = new GoblinToken();
                break;
            default:
                token = new SaprolingToken();
                break;
        }
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
        return true;
    }
}
