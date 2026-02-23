package mage.cards.u;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class Unbury extends CardImpl {

    public Unbury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Choose one --
        // * Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // * Return two target creature cards that share a creature type from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new UnburyTarget());
        this.getSpellAbility().addMode(mode);
    }

    private Unbury(final Unbury card) {
        super(card);
    }

    @Override
    public Unbury copy() {
        return new Unbury(this);
    }
}

class UnburyTarget extends TargetCardInYourGraveyard {

    UnburyTarget() {
        super(2, 2, StaticFilters.FILTER_CARD_CREATURE, false);
        targetName = "creature cards that share a creature type from your graveyard";
    }

    private UnburyTarget(final UnburyTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        if (getTargets().isEmpty()) {
            return true;
        }
        Card targetOne = game.getCard(getTargets().get(0));
        Card targetTwo = game.getCard(id);
        if (targetOne == null || targetTwo == null) {
            return false;
        }
        return targetOne.shareCreatureTypes(game, targetTwo);
    }

    @Override
    public boolean canChoose(UUID sourceControllerId, Ability source, Game game) {
        MageObject targetSource = game.getObject(source);
        Player player = game.getPlayer(sourceControllerId);
        if (player == null) {
            return false;
        }
        if (targetSource == null) {
            return false;
        }
        List<Card> cards = player.getGraveyard().getCards(
                filter, sourceControllerId, source, game
        ).stream().collect(Collectors.toList());
        if (cards.size() < 2) {
            return false;
        }
        for (int i = 0; i < cards.size(); i++) {
            for (int j = 0; j < cards.size(); j++) {
                if (i <= j) {
                    continue;
                }
                if (cards.get(i).shareCreatureTypes(game, cards.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public UnburyTarget copy() {
        return new UnburyTarget(this);
    }
}
