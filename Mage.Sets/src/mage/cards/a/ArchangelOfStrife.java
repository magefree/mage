package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Eirkei
 */
public final class ArchangelOfStrife extends CardImpl {

    private static final FilterPermanent warFilter = new FilterCreaturePermanent("Creatures controlled by players who chose war");
    private static final FilterPermanent peaceFilter = new FilterCreaturePermanent("Creatures controlled by players who chose peace");

    static {
        warFilter.add(WarPredicate.instance);
        peaceFilter.add(PeacePredicate.instance);
    }

    public ArchangelOfStrife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As Archangel of Strife enters the battlefield, each player chooses war or peace.
        this.addAbility(new AsEntersBattlefieldAbility(new ArchangelOfStrifeChooseEffect()));

        // Creatures controlled by players who chose war get +3/+0.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(3, 0, Duration.WhileOnBattlefield, warFilter)));

        // Creatures controlled by players who chose peace get +0/+3.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(0, 3, Duration.WhileOnBattlefield, peaceFilter)));
    }

    private ArchangelOfStrife(final ArchangelOfStrife card) {
        super(card);
    }

    @Override
    public ArchangelOfStrife copy() {
        return new ArchangelOfStrife(this);
    }
}

class ArchangelOfStrifeChooseEffect extends OneShotEffect {

    ArchangelOfStrifeChooseEffect() {
        super(Outcome.Neutral);

        this.staticText = "each player chooses war or peace.";
    }

    private ArchangelOfStrifeChooseEffect(final ArchangelOfStrifeChooseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());

        if (sourcePermanent == null) {
            sourcePermanent = game.getPermanentEntering(source.getSourceId());
        }

        if (controller != null && sourcePermanent != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);

                Choice choice = new ChoiceImpl(true);
                choice.setMessage("Select war or peace");
                choice.getChoices().add("war");
                choice.getChoices().add("peace");

                if (!player.choose(Outcome.Neutral, choice, game)) {
                    continue;
                }
                if (!game.isSimulation()) {
                    game.informPlayers(sourcePermanent.getLogName() + ": " + player.getLogName() + " has chosen " + choice.getChoice());
                }

                game.getState().setValue(playerId + "_" + source.getSourceId() + "_modeChoice", choice.getChoice());
                sourcePermanent.addInfo("_" + playerId + "_modeChoice", "<font color = 'blue'>" + player.getName() + " chose: " + choice.getChoice() + "</font>", game);
            }
            return true;
        }
        return false;
    }

    @Override
    public ArchangelOfStrifeChooseEffect copy() {
        return new ArchangelOfStrifeChooseEffect(this);
    }

}

enum WarPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        UUID controllerId = ((Permanent) input.getObject()).getControllerId();
        String chosenMode = (String) game.getState().getValue(controllerId + "_" + input.getSourceId() + "_modeChoice");
        return chosenMode != null && chosenMode.equals("war");
    }
}

enum PeacePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        UUID controllerId = ((Permanent) input.getObject()).getControllerId();
        String chosenMode = (String) game.getState().getValue(controllerId + "_" + input.getSourceId() + "_modeChoice");
        return chosenMode != null && chosenMode.equals("peace");
    }
}
