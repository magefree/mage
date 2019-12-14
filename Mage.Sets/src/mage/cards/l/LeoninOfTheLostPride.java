package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.target.common.TargetCardInOpponentsGraveyard;

import java.util.UUID;

/**
 * @author jmharmon
 */

public final class LeoninOfTheLostPride extends CardImpl {

    public LeoninOfTheLostPride(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Leonin of the Lost Pride dies, exile target card from an opponent’s graveyard.
        DiesTriggeredAbility diesTriggeredAbility = new DiesTriggeredAbility(new ExileTargetEffect());
        diesTriggeredAbility.addTarget(new TargetCardInOpponentsGraveyard(new FilterCard("card from an opponent's graveyard")));
        this.addAbility(diesTriggeredAbility);
    }

    public LeoninOfTheLostPride(final LeoninOfTheLostPride card) {
        super(card);
    }

    @Override
    public LeoninOfTheLostPride copy() {
        return new LeoninOfTheLostPride(this);
    }
}
