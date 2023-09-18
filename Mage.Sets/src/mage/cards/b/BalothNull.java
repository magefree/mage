package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class BalothNull extends CardImpl {

    public BalothNull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{G}");
        this.subtype.add(SubType.ZOMBIE, SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Baloth Null enters the battlefield, return up to two target creature cards from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(0, 2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private BalothNull(final BalothNull card) {
        super(card);
    }

    @Override
    public BalothNull copy() {
        return new BalothNull(this);
    }
}
