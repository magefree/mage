package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SubTypeSet;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
        targetName = "creature cards that share a creature type";
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
        return targetOne.shareSubtypes(targetTwo, game);
    }

    @Override
    public boolean canChoose(UUID sourceId, UUID sourceControllerId, Game game) {
        Set<SubType> subTypes = new HashSet<>();
        MageObject targetSource = game.getObject(sourceId);
        Player player = game.getPlayer(sourceControllerId);
        if (player == null) {
            return false;
        }
        if (targetSource == null) {
            return false;
        }
        for (Card card : player.getGraveyard().getCards(filter, sourceId, sourceControllerId, game)) {
            if (card.isAllCreatureTypes() || card.getAbilities(game).contains(ChangelingAbility.getInstance())) {
                if (!subTypes.isEmpty()) {
                    return true;
                } else {
                    subTypes.addAll(SubType.getCreatureTypes());
                }
                continue;
            }
            for (SubType subType : card.getSubtype(game)) {
                if (subType.getSubTypeSet() == SubTypeSet.CreatureType && subTypes.contains(subType)) {
                    return true;
                }
            }
            subTypes.addAll(card.getSubtype(game));
            subTypes.removeIf((SubType st) -> (st.getSubTypeSet() != SubTypeSet.CreatureType));
        }
        return false;
    }

    @Override
    public ReturnFromExtinctionTarget copy() {
        return new ReturnFromExtinctionTarget(this);
    }
}
