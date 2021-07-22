package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArchonOfFallingStars extends CardImpl {

    private static final FilterCard filter = new FilterEnchantmentCard("enchantment card from your graveyard");

    public ArchonOfFallingStars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ARCHON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Archon of Falling Stars dies, you may return target enchantment card from your graveyard to the battlefield.
        Ability ability = new DiesSourceTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private ArchonOfFallingStars(final ArchonOfFallingStars card) {
        super(card);
    }

    @Override
    public ArchonOfFallingStars copy() {
        return new ArchonOfFallingStars(this);
    }
}
