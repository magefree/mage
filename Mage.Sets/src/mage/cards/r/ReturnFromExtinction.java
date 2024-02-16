package mage.cards.r;

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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ReturnFromExtinction extends CardImpl {

    public ReturnFromExtinction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Choose one —
        // • Return target creature card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        // • Return two target creature cards that share a creature type from your graveyard to your hand.
        Mode mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new ReturnFromExtinctionTarget());
        this.getSpellAbility().addMode(mode);
    }

    private ReturnFromExtinction(final ReturnFromExtinction card) {
        super(card);
    }

    @Override
    public ReturnFromExtinction copy() {
        return new ReturnFromExtinction(this);
    }
}

class ReturnFromExtinctionTarget extends TargetCardInYourGraveyard {

    ReturnFromExtinctionTarget() {
        super(2, 2, StaticFilters.FILTER_CARD_CREATURE, false);
        targetName = "creature cards that share a creature type from your graveyard";
    }

    private ReturnFromExtinctionTarget(final ReturnFromExtinctionTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
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
    public ReturnFromExtinctionTarget copy() {
        return new ReturnFromExtinctionTarget(this);
    }
}
