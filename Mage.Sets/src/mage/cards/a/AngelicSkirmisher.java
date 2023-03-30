
package mage.cards.a;

import java.util.*;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class AngelicSkirmisher extends CardImpl {

    public AngelicSkirmisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each combat, choose first strike, vigilance or lifelink. Creatures you control gain that ability until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new AngelicSkirmisherEffect(), TargetController.ANY, false);
        this.addAbility(ability);
    }

    private AngelicSkirmisher(final AngelicSkirmisher card) {
        super(card);
    }

    @Override
    public AngelicSkirmisher copy() {
        return new AngelicSkirmisher(this);
    }
}

class AngelicSkirmisherEffect extends OneShotEffect {

    AngelicSkirmisherEffect() {
        super(Outcome.AddAbility);
        staticText = "choose first strike, vigilance or lifelink. Creatures you control gain that ability until end of turn";
    }

    AngelicSkirmisherEffect(final AngelicSkirmisherEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            Choice abilityChoice = new ChoiceImpl(true);
            Set<String> abilityChoices = new LinkedHashSet<>(3);
            abilityChoice.setMessage("Choose ability for your creatures");
            abilityChoices.add("First strike");
            abilityChoices.add("Vigilance");
            abilityChoices.add("Lifelink");
            abilityChoice.setChoices(abilityChoices);
            if (controller.choose(outcome, abilityChoice, game)) {
                Ability ability = null;
                switch (abilityChoice.getChoice()) {
                    case "First strike":
                        ability = FirstStrikeAbility.getInstance();
                        break;
                    case "Vigilance":
                        ability = VigilanceAbility.getInstance();
                        break;
                    case "Lifelink":
                        ability = LifelinkAbility.getInstance();
                        break;
                    default:
                        break;
                }
                if (ability != null) {
                    GainAbilityControlledEffect effect = new GainAbilityControlledEffect(ability, Duration.EndOfTurn, new FilterControlledCreaturePermanent());
                    game.addEffect(effect, source);
                    game.informPlayers(sourcePermanent.getName() + ": " + controller.getLogName() + " has chosen " + abilityChoice.getChoice().toLowerCase(Locale.ENGLISH));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public AngelicSkirmisherEffect copy() {
        return new AngelicSkirmisherEffect(this);
    }
}
