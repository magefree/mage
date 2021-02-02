
package mage.cards.g;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.RampageAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Styxo & L_J
 */
public final class GabrielAngelfire extends CardImpl {

    public GabrielAngelfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}{W}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, choose flying, first strike, trample, or rampage 3. Gabriel Angelfire gains that ability until your next upkeep.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GabrielAngelfireGainAbilityEffect(), TargetController.YOU, false));
    }

    private GabrielAngelfire(final GabrielAngelfire card) {
        super(card);
    }

    @Override
    public GabrielAngelfire copy() {
        return new GabrielAngelfire(this);
    }
}

class GabrielAngelfireGainAbilityEffect extends GainAbilitySourceEffect {

    private static final Set<String> choices = new LinkedHashSet<>();
    private boolean sameStep = true;

    static {
        choices.add("Flying");
        choices.add("First strike");
        choices.add("Trample");
        choices.add("Rampage 3");
    }

    public GabrielAngelfireGainAbilityEffect() {
        super(FlyingAbility.getInstance(), Duration.Custom);
        staticText = "choose flying, first strike, trample, or rampage 3. {this} gains that ability until your next upkeep";
    }

    public GabrielAngelfireGainAbilityEffect(final GabrielAngelfireGainAbilityEffect effect) {
        super(effect);
        ability.newId();
    }

    @Override
    public GabrielAngelfireGainAbilityEffect copy() {
        return new GabrielAngelfireGainAbilityEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UPKEEP) {
            if (!sameStep && game.isActivePlayer(source.getControllerId()) || game.getPlayer(source.getControllerId()).hasReachedNextTurnAfterLeaving()) {
                return true;
            }
        } else {
            sameStep = false;
        }
        return false;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Choose one");
            choice.setChoices(choices);
            if (controller.choose(outcome, choice, game)) {
                switch (choice.getChoice()) {
                    case "First strike":
                        ability = FirstStrikeAbility.getInstance();
                        break;
                    case "Trample":
                        ability = TrampleAbility.getInstance();
                        break;
                    case "Rampage 3":
                        ability = new RampageAbility(3);
                        break;
                    default:
                        ability = FlyingAbility.getInstance();
                        break;
                }
            } else {
                discard();
            }
        }
    }

}
