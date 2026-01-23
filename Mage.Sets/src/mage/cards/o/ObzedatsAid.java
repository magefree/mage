package mage.cards.o;

import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ObzedatsAid extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public ObzedatsAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}{B}");

        // Return target permanent card from your graveyard to the battlefield.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

    }

    private ObzedatsAid(final ObzedatsAid card) {
        super(card);
    }

    @Override
    public ObzedatsAid copy() {
        return new ObzedatsAid(this);
    }
}
