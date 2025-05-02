package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrderOfMidnight extends AdventureCard {

    public OrderOfMidnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.SORCERY}, "{1}{B}","Alter Fate", "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Order of Midnight can't block.
        this.addAbility(new CantBlockAbility());

        // Alter Fate
        // Return target creature card from your graveyard to your hand.
        this.getSpellCard().getSpellAbility().addEffect(new ReturnFromGraveyardToHandTargetEffect());
        this.getSpellCard().getSpellAbility().addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));

        this.finalizeAdventure();
    }

    private OrderOfMidnight(final OrderOfMidnight card) {
        super(card);
    }

    @Override
    public OrderOfMidnight copy() {
        return new OrderOfMidnight(this);
    }
}
