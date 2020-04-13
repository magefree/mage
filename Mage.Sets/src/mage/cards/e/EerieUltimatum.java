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
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

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
        if (!player.choose(outcome, player.getGraveyard(), target, game)) {
            return false;
        }
        return player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
    }
}

class EerieUltimatumTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter
            = new FilterPermanentCard("permanent cards with different names");

    EerieUltimatumTarget() {
        super(0, Integer.MAX_VALUE, filter, true);
    }

    private EerieUltimatumTarget(final EerieUltimatumTarget target) {
        super(target);
    }

    @Override
    public EerieUltimatumTarget copy() {
        return new EerieUltimatumTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        return this.getTargets()
                .stream()
                .map(game::getCard)
                .map(MageObject::getName)
                .noneMatch(card.getName()::equals);
    }
}
