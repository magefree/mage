package mage.cards.w;

import mage.MageInt;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{2}{W}", "Mend the Wilds", "{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Woodland Acolyte enters the battlefield, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // Mend the Wilds
        // Put target permanent card from your graveyard on top of your library.
        this.getSpellCard().getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(filter));

        this.finalizeAdventure();
    }

    private WoodlandAcolyte(final WoodlandAcolyte card) {
        super(card);
    }

    @Override
    public WoodlandAcolyte copy() {
        return new WoodlandAcolyte(this);
    }
}
