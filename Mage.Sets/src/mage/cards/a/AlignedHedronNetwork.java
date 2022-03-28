
package mage.cards.a;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class AlignedHedronNetwork extends CardImpl {

    public AlignedHedronNetwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // When Aligned Hedron Network enters the battlefield, exile all creatures with power 5 or greater until Aligned Hedron Network leaves the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AlignedHedronNetworkExileEffect(), false));
    }

    private AlignedHedronNetwork(final AlignedHedronNetwork card) {
        super(card);
    }

    @Override
    public AlignedHedronNetwork copy() {
        return new AlignedHedronNetwork(this);
    }
}

class AlignedHedronNetworkExileEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 5 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 4));
    }

    public AlignedHedronNetworkExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile all creatures with power 5 or greater until {this} leaves the battlefield";
    }

    public AlignedHedronNetworkExileEffect(final AlignedHedronNetworkExileEffect effect) {
        super(effect);
    }

    @Override
    public AlignedHedronNetworkExileEffect copy() {
        return new AlignedHedronNetworkExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) { return false;}

        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) { return false; }

        // If Whale leaves the battlefield before its triggered ability resolves,
        // the target creature won't be exiled.
        Set<Card> toExile = new LinkedHashSet<>(game.getBattlefield().getActivePermanents(filter, controller.getId(), source, game));
        if (toExile.isEmpty()) { return false; }
        
        controller.moveCardsToExile(toExile, source, game, true, CardUtil.getCardExileZoneId(game, source), permanent.getIdName());
        new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()).apply(game, source);

        return true;

    }
}
