package mage.cards.c;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class CouncilGuardian extends CardImpl {

    public CouncilGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Will of the council - When Council Guardian enters the battlefield, starting with you, each player votes for blue, black, red, or green. Council Guardian gains protection from each color with the most votes or tied for most votes.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CouncilsGuardianEffect(), false, "<i>Will of the council</i> &mdash; "));

    }

    public CouncilGuardian(final CouncilGuardian card) {
        super(card);
    }

    @Override
    public CouncilGuardian copy() {
        return new CouncilGuardian(this);
    }
}

class CouncilsGuardianEffect extends OneShotEffect {

    public CouncilsGuardianEffect() {
        super(Outcome.Benefit);
        this.staticText = "starting with you, each player votes for blue, black, red, or green. {this} gains protection from each color with the most votes or tied for most votes";
    }

    public CouncilsGuardianEffect(final CouncilsGuardianEffect effect) {
        super(effect);
    }

    @Override
    public CouncilsGuardianEffect copy() {
        return new CouncilsGuardianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        ChoiceColor choice = new ChoiceColor();
        choice.getChoices().remove("White");
        if (controller != null) {
            Map<ObjectColor, Integer> chosenColors = new HashMap<>(2);
            int maxCount = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    choice.clearChoice();
                    if (player.choose(Outcome.Detriment, choice, game)) {
                        ObjectColor color = choice.getColor();
                        if (color != null) {
                            if (chosenColors.containsKey(color)) {
                                int count = chosenColors.get(color) + 1;
                                if (count > maxCount) {
                                    maxCount = count;
                                }
                                chosenColors.put(color, count);
                            } else {
                                if (maxCount == 0) {
                                    maxCount = 1;
                                }
                                chosenColors.put(color, 1);
                            }
                            game.informPlayers(player.getLogName() + " has chosen " + color.getDescription() + '.');
                        }
                    }
                }
            }

            for (Map.Entry<ObjectColor, Integer> entry : chosenColors.entrySet()) {
                if (entry.getValue() == maxCount) {
                    ObjectColor color = entry.getKey();
                    game.addEffect(new GainAbilitySourceEffect(ProtectionAbility.from(color), Duration.Custom), source);
                }
            }
            return true;
        }
        return false;
    }
}
