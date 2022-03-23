
package mage.cards.m;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class MarchFromTheTomb extends CardImpl {

    public MarchFromTheTomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{B}");

        // Return any number of target Ally creature cards with total converted mana cost of 8 or less from your graveyard to the battlefield.
        Effect effect = new ReturnFromGraveyardToBattlefieldTargetEffect();
        effect.setText("Return any number of target Ally creature cards with total mana value 8 or less from your graveyard to the battlefield");
        this.getSpellAbility().addEffect(effect);
        FilterCard filter = new FilterCreatureCard();
        filter.add(SubType.ALLY.getPredicate());
        this.getSpellAbility().addTarget(new MarchFromTheTombTarget(0, Integer.MAX_VALUE, filter));
    }

    private MarchFromTheTomb(final MarchFromTheTomb card) {
        super(card);
    }

    @Override
    public MarchFromTheTomb copy() {
        return new MarchFromTheTomb(this);
    }
}

class MarchFromTheTombTarget extends TargetCardInYourGraveyard {

    public MarchFromTheTombTarget(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, filter);
    }

    public MarchFromTheTombTarget(MarchFromTheTombTarget target) {
        super(target);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        int cmcLeft = 8;
        for (UUID targetId : this.getTargets()) {
            Card card = game.getCard(targetId);
            if (card != null) {
                cmcLeft -= card.getManaValue();
            }
        }
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        Set<UUID> leftPossibleTargets = new HashSet<>();
        for (UUID targetId : possibleTargets) {
            Card card = game.getCard(targetId);
            if (card != null && card.getManaValue() <= cmcLeft) {
                leftPossibleTargets.add(targetId);
            }
        }
        setTargetName("any number of target Ally creature cards with total mana value of 8 or less (" + cmcLeft + " left) from your graveyard");
        return leftPossibleTargets;
    }

    @Override
    public boolean canTarget(UUID playerId, UUID objectId, Ability source, Game game) {
        if (super.canTarget(playerId, objectId, source, game)) {
            int cmcLeft = 8;
            for (UUID targetId : this.getTargets()) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    cmcLeft -= card.getManaValue();
                }
            }
            Card card = game.getCard(objectId);
            return card != null && card.getManaValue() <= cmcLeft;
        }
        return false;
    }

    @Override
    public MarchFromTheTombTarget copy() {
        return new MarchFromTheTombTarget(this);
    }

}
