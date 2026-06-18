package mage.cards.a;

import java.util.UUID;

import mage.abilities.condition.common.ControlYourCommanderCondition;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class AncestralCommunion extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public AncestralCommunion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // When you cast this spell while you control your commander, copy this spell. You may choose a new target for the copy.
        this.addAbility(new CastSourceTriggeredAbility(
            new CopySourceSpellEffect().setText("copy this spell. You may choose a new target for the copy")
        ).withTriggerCondition(ControlYourCommanderCondition.instance));

        // Return target permanent card from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));
    }

    private AncestralCommunion(final AncestralCommunion card) {
        super(card);
    }

    @Override
    public AncestralCommunion copy() {
        return new AncestralCommunion(this);
    }
}
