package mage.choices;

import mage.MageObject;
import mage.abilities.Ability;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Game's choose dialog to ask about creature type.
 * <p>
 * Warning, must use getChoiceKey as result
 */
public class ChoiceCreatureType extends ChoiceImpl {

    private static final String DEFAULT_MESSAGE = "Choose a creature type";

    public ChoiceCreatureType(Game game, Ability source) {
        this(game, source, true, DEFAULT_MESSAGE);
    }

    public ChoiceCreatureType(Game game, Ability source, boolean required, String chooseMessage) {
        super(required);
        this.setMessage(chooseMessage);
        MageObject sourceObject = source == null ? null : game.getObject(source);
        if (sourceObject != null) {
            this.setSubMessage(sourceObject.getLogName());
        }
        this.setSearchEnabled(true);        

        // collect basic info
        // additional info will be added onChooseStart
        SubType.getCreatureTypes().stream().map(SubType::toString).forEach(value -> {
            this.withItem(value, value, null, null, null);
        });
    }

    protected ChoiceCreatureType(final ChoiceCreatureType choice) {
        super(choice);
    }

    @Override
    public ChoiceCreatureType copy() {
        return new ChoiceCreatureType(this);
    }

    @Override
    public void onChooseStart(Game game, UUID choosingPlayerId) {
        // add additional info about important creature types (collect it from all public zone and own hand)
        Set<String> myTypes = new HashSet<>();
        Set<String> opponentTypes = new HashSet<>();

        game.getState().getPlayersInRange(choosingPlayerId, game).forEach(playerId -> {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                return;
            }

            Set<String> list = playerId.equals(choosingPlayerId) ? myTypes : opponentTypes;

            // own hand
            if (playerId.equals(choosingPlayerId)) {
                player.getHand().getCards(game).forEach(card -> {
                    list.addAll(card.getSubtype(game).stream().map(SubType::toString).collect(Collectors.toList()));
                });
            }

            // battlefield
            game.getBattlefield().getAllActivePermanents(playerId).forEach(permanent -> {
                list.addAll(permanent.getSubtype(game).stream().map(SubType::toString).collect(Collectors.toList()));
            });

            // graveyard
            player.getGraveyard().getCards(game).forEach(card -> {
                list.addAll(card.getSubtype(game).stream().map(SubType::toString).collect(Collectors.toList()));
            });

            // exile
            game.getExile().getCardsOwned(game, playerId).forEach(card -> {
                list.addAll(card.getSubtype(game).stream().map(SubType::toString).collect(Collectors.toList()));
            });
        });

        // stack
        game.getStack().forEach(stackObject -> {
            if (stackObject.isControlledBy(choosingPlayerId)) {
                myTypes.addAll(stackObject.getSubtype(game).stream().map(SubType::toString).collect(Collectors.toList()));
            } else {
                opponentTypes.addAll(stackObject.getSubtype(game).stream().map(SubType::toString).collect(Collectors.toList()));
            }
        });

        // sort order: me -> opponent -> not used
        this.keyChoices.forEach((key, value) -> {
            String additionalInfo = "";
            Integer orderPriority = 0;
            if (myTypes.contains(key)) {
                additionalInfo += "me";
                orderPriority -= 100;
            }
            if (opponentTypes.contains(key)) {
                if (!additionalInfo.isEmpty()) {
                    additionalInfo += ", ";
                }
                additionalInfo += "opponent";
                orderPriority -= 10;
            }

            this.keyChoices.put(key, key + (additionalInfo.isEmpty() ? "" : " (" + additionalInfo + ")"));
            this.sortData.put(key, orderPriority);
        });
    }
}
