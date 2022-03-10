package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author hanasu
 */
public final class MonkIdealist extends CardImpl {

    private static final FilterCard filter = new FilterEnchantmentCard("enchantment card from your graveyard");

    public MonkIdealist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Monk Idealist enters the battlefield, return target enchantment card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private MonkIdealist(final MonkIdealist card) {
        super(card);
    }

    @Override
    public MonkIdealist copy() {
        return new MonkIdealist(this);
    }
}
