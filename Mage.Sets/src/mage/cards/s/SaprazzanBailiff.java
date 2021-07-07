
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandFromGraveyardAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class SaprazzanBailiff extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("artifact and enchantment cards");
    
    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()));
    }

    public SaprazzanBailiff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Saprazzan Bailiff enters the battlefield, exile all artifact and enchantment cards from all graveyards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SaprazzanBailiffEffect()));

        // When Saprazzan Bailiff leaves the battlefield, return all artifact and enchantment cards from all graveyards to their owners' hands.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ReturnToHandFromGraveyardAllEffect(filter).setText("return all artifact and enchantment cards from all graveyards to their owners' hands"), false));
    }

    private SaprazzanBailiff(final SaprazzanBailiff card) {
        super(card);
    }

    @Override
    public SaprazzanBailiff copy() {
        return new SaprazzanBailiff(this);
    }
}

class SaprazzanBailiffEffect extends OneShotEffect {

    public SaprazzanBailiffEffect() {
        super(Outcome.Detriment);
        staticText = "exile all artifact and enchantment cards from all graveyards";
    }

    private SaprazzanBailiffEffect(final SaprazzanBailiffEffect effect) {
        super(effect);
    }

    @Override
    public SaprazzanBailiffEffect copy() {
        return new SaprazzanBailiffEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                for (UUID cid : player.getGraveyard().copy()) {
                    Card card = game.getCard(cid);
                    if (card != null && (card.isArtifact(game) || card.isEnchantment(game))) {
                        controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.GRAVEYARD, true);
                    }
                }
            }
        }
        return true;
    }
}
