package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

/**
 * @author TheElk801
 */
public class CreateXXTokenExiledEffectManaValueEffect extends OneShotEffect {

    private final Function<Integer, Token> tokenMaker;

    public CreateXXTokenExiledEffectManaValueEffect(Function<Integer, Token> tokenMaker, String description) {
        super(Outcome.Benefit);
        this.tokenMaker = tokenMaker;
        staticText = "the exiled card's owner creates an X/X " + description +
                " creature token, where X is the mana value of the exiled card";
    }

    private CreateXXTokenExiledEffectManaValueEffect(final CreateXXTokenExiledEffectManaValueEffect effect) {
        super(effect);
        this.tokenMaker = effect.tokenMaker;
    }

    @Override
    public CreateXXTokenExiledEffectManaValueEffect copy() {
        return new CreateXXTokenExiledEffectManaValueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanentLeftBattlefield = (Permanent) getValue("permanentLeftBattlefield");
        ExileZone exile = game.getExile().getExileZone(
                CardUtil.getExileZoneId(game, source.getSourceId(), permanentLeftBattlefield.getZoneChangeCounter(game))
        );
        if (exile == null || exile.isEmpty()) {
            return false;
        }
        // From ZNR Release Notes:
        // https://magic.wizards.com/en/articles/archive/feature/zendikar-rising-release-notes-2020-09-10
        // If Skyclave Apparition's first ability exiled more than one card owned by a single player,
        // that player creates a token with power and toughness equal to the sum of those cards' converted mana costs.
        // If the first ability exiled cards owned by more than one player, each of those players creates a token
        // with power and toughness equal to the sum of the converted mana costs of all cards exiled by the first ability.
        Set<UUID> owners = new HashSet<>();
        int totalCMC = exile
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .map(card -> {
                    owners.add(card.getOwnerId());
                    return card;
                })
                .mapToInt(MageObject::getManaValue)
                .sum();
        for (UUID playerId : owners) {
            tokenMaker.apply(totalCMC).putOntoBattlefield(1, game, source, playerId);
        }
        return true;
    }
}
