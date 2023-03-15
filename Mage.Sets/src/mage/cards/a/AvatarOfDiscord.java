
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author Plopman
 */
public final class AvatarOfDiscord extends CardImpl {

    public AvatarOfDiscord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B/R}{B/R}{B/R}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // <i>({BR} can be paid with either {B} or {R}.)</i>
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Avatar of Discord enters the battlefield, sacrifice it unless you discard two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS)))));
    }

    private AvatarOfDiscord(final AvatarOfDiscord card) {
        super(card);
    }

    @Override
    public AvatarOfDiscord copy() {
        return new AvatarOfDiscord(this);
    }
}
