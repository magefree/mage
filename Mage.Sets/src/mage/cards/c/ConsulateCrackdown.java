
package mage.cards.c;

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
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author JRHerlehy
 */
public final class ConsulateCrackdown extends CardImpl {

    public ConsulateCrackdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{W}");


        // When Consulate Crackdown enters the battlefield, exile all artifacts your opponents control until Consulate Crackdown leaves the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConsulateCracksownExileEffect(), false));
    }

    private ConsulateCrackdown(final ConsulateCrackdown card) {
        super(card);
    }

    @Override
    public ConsulateCrackdown copy() {
        return new ConsulateCrackdown(this);
    }
}

class ConsulateCracksownExileEffect extends OneShotEffect {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("artifacts your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ConsulateCracksownExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile all artifacts your opponents control until {this} leaves the battlefield";
    }

    ConsulateCracksownExileEffect(final ConsulateCracksownExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);

        //If the permanent leaves the battlefield before the ability resolves, artifacts won't be exiled.
        if (permanent == null || controller == null) return false;

        Set<Card> toExile = new LinkedHashSet<>();
        for (Permanent artifact : game.getBattlefield().getActivePermanents(filter, controller.getId(), game)) {
            toExile.add(artifact);
        }

        if (!toExile.isEmpty()) {
            controller.moveCardsToExile(toExile, source, game, true, CardUtil.getCardExileZoneId(game, source), permanent.getIdName());
            new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()).apply(game, source);
        }

        return true;
    }

    @Override
    public ConsulateCracksownExileEffect copy() {
        return new ConsulateCracksownExileEffect(this);
    }
}
