package mage.cards.e;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.SoldierArtifactToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmergencyWeld extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifact or creature card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public EmergencyWeld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Return target artifact or creature card from your graveyard to your hand. Create a 1/1 colorless Soldier artifact creature token.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
        this.getSpellAbility().addEffect(new CreateTokenEffect(new SoldierArtifactToken()));
    }

    private EmergencyWeld(final EmergencyWeld card) {
        super(card);
    }

    @Override
    public EmergencyWeld copy() {
        return new EmergencyWeld(this);
    }
}
