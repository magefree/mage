package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceBasicLandType;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author L_J
 */
public final class GiantSlug extends CardImpl {

    public GiantSlug(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.SLUG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {5}: At the beginning of your next upkeep, choose a basic land type. Giant Slug gains landwalk of the chosen type until the end of that turn.
        this.addAbility(new SimpleActivatedAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(new GiantSlugEffect())
        ), new ManaCostsImpl<>("{5}")));
    }

    private GiantSlug(final GiantSlug card) {
        super(card);
    }

    @Override
    public GiantSlug copy() {
        return new GiantSlug(this);
    }
}

class GiantSlugEffect extends OneShotEffect {

    GiantSlugEffect() {
        super(Outcome.AddAbility);
        this.staticText = "At the beginning of your next upkeep, choose a basic land type. " +
                "{this} gains landwalk of the chosen type until the end of that turn";
    }

    private GiantSlugEffect(final GiantSlugEffect effect) {
        super(effect);
    }

    @Override
    public GiantSlugEffect copy() {
        return new GiantSlugEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller == null || sourcePermanent == null) {
            return false;
        }
        ChoiceImpl choices = new ChoiceBasicLandType();
        if (!controller.choose(outcome, choices, game)) {
            return false;
        }
        game.informPlayers(sourcePermanent.getName() + ":  Chosen basic land type is " + choices.getChoice());
        switch (choices.getChoice()) {
            case "Plains":
                game.addEffect(new GainAbilitySourceEffect(
                        new PlainswalkAbility(), Duration.EndOfTurn, false
                ), source);
                return true;
            case "Island":
                game.addEffect(new GainAbilitySourceEffect(
                        new IslandwalkAbility(), Duration.EndOfTurn, false
                ), source);
                return true;
            case "Swamp":
                game.addEffect(new GainAbilitySourceEffect(
                        new SwampwalkAbility(), Duration.EndOfTurn, false
                ), source);
                return true;
            case "Mountain":
                game.addEffect(new GainAbilitySourceEffect(
                        new MountainwalkAbility(), Duration.EndOfTurn, false
                ), source);
                return true;
            case "Forest":
                game.addEffect(new GainAbilitySourceEffect(
                        new ForestwalkAbility(), Duration.EndOfTurn, false
                ), source);
                return true;
            default:
                return false;
        }
    }
}
