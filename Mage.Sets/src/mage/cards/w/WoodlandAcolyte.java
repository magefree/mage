package mage.cards.w;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WoodlandAcolyte extends AdventureCard {

    private static final FilterCard filter = new FilterPermanentCard("permanent card from your graveyard");

    public WoodlandAcolyte(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{2}{W}",
                "Mend the Wilds",
                new CardType[]{CardType.INSTANT}, "{G}");

        // Woodland Acolyte
        this.getLeftHalfCard().setPT(2, 2);

        // When Woodland Acolyte enters the battlefield, draw a card.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Mend the Wilds
        // Put target permanent card from your graveyard on top of your library.
        this.getRightHalfCard().getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        finalizeCard();
    }

    private WoodlandAcolyte(final WoodlandAcolyte card) {
        super(card);
    }

    @Override
    public WoodlandAcolyte copy() {
        return new WoodlandAcolyte(this);
    }
}
