package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NurglesConscription extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card from an opponent's graveyard");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public NurglesConscription(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Put target creature card from an opponent's graveyard onto the battlefield tapped under your control, then exile that player's graveyard.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect(true));
        this.getSpellAbility().addEffect(new NurglesConscriptionEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    private NurglesConscription(final NurglesConscription card) {
        super(card);
    }

    @Override
    public NurglesConscription copy() {
        return new NurglesConscription(this);
    }
}

class NurglesConscriptionEffect extends OneShotEffect {

    NurglesConscriptionEffect() {
        super(Outcome.Benefit);
        staticText = ", then exile that player's graveyard";
    }

    private NurglesConscriptionEffect(final NurglesConscriptionEffect effect) {
        super(effect);
    }

    @Override
    public NurglesConscriptionEffect copy() {
        return new NurglesConscriptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getOwnerId(getTargetPointer().getFirst(game, source)));
        return player != null && player.moveCards(player.getGraveyard(), Zone.EXILED, source, game);
    }
}
