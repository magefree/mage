package mage.cards.e;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class EerieUltimatum extends CardImpl {

    public EerieUltimatum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{W}{W}{B}{B}{B}{G}{G}");

        // Return any number of permanent cards with different names from your graveyard to the battlefield
        this.getSpellAbility().addEffect(new EerieUltimatumEffect());
    }

    private EerieUltimatum(final EerieUltimatum card) {
        super(card);
    }

    @Override
    public EerieUltimatum copy() {
        return new EerieUltimatum(this);
    }
}

class EerieUltimatumEffect extends OneShotEffect {

    EerieUltimatumEffect() {
        super(Outcome.Benefit);
        staticText = "return any number of permanent cards with different names from your graveyard to the battlefield";
    }

    private EerieUltimatumEffect(final EerieUltimatumEffect effect) {
        super(effect);
    }

    @Override
    public EerieUltimatumEffect copy() {
        return new EerieUltimatumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new EerieUltimatumTarget();
        if (!player.choose(outcome, player.getGraveyard(), target, source, game)) {
            return false;
        }
        return player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
    }
}

class EerieUltimatumTarget extends TargetCardInYourGraveyard {

    EerieUltimatumTarget() {
        super(0, Integer.MAX_VALUE, new FilterPermanentCard("permanent cards with different names"), true);
    }

    private EerieUltimatumTarget(final EerieUltimatumTarget target) {
        super(target);
    }

    @Override
    public EerieUltimatumTarget copy() {
        return new EerieUltimatumTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability ability, Game game) {
        if (super.canTarget(playerId, id, ability, game)) {
            Set<String> names = this.getTargets()
                .stream()
                .map(game::getCard)
                .map(MageObject::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
            Card card = game.getCard(id);
            return card != null && !names.contains(card.getName());            
        }
        return false;
    }

    
    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<String> names = this.getTargets()
                .stream()
                .map(game::getCard)
                .map(MageObject::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        possibleTargets.removeIf(uuid -> {
            Card card = game.getCard(uuid);
            return card != null && names.contains(card.getName());
        });
        return possibleTargets;
    }
}
