package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ChooseACardInYourHandItPerpetuallyGainsEffect;
import mage.constants.SubType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author anonymous
 */
public final class RecklessRingleader extends CardImpl {

    public RecklessRingleader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Reckless Ringleader enters the battlefield, choose a creature card in your hand. It perpetually gains haste.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ChooseACardInYourHandItPerpetuallyGainsEffect(HasteAbility.getInstance(), new FilterCreatureCard())));
    }

    private RecklessRingleader(final RecklessRingleader card) {
        super(card);
    }

    @Override
    public RecklessRingleader copy() {
        return new RecklessRingleader(this);
    }
}
