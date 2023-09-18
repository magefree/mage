package mage.cards.l;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class LifeFromTheLoam extends CardImpl {

    public LifeFromTheLoam(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Return up to three target land cards from your graveyard to your hand.
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0, 3, new FilterLandCard("land cards from your graveyard")));
        // Dredge 3 (If you would draw a card, instead you may put exactly three cards from the top of your library into your graveyard. If you do, return this card from your graveyard to your hand. Otherwise, draw a card.)
        this.addAbility(new DredgeAbility(3));
    }

    private LifeFromTheLoam(final LifeFromTheLoam card) {
        super(card);
    }

    @Override
    public LifeFromTheLoam copy() {
        return new LifeFromTheLoam(this);
    }
}
