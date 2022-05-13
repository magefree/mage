package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect.PutCards;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactOrEnchantmentCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArcanistsOwl extends CardImpl {

    private static final FilterCard filter = new FilterArtifactOrEnchantmentCard();

    public ArcanistsOwl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W/U}{W/U}{W/U}{W/U}");

        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Arcanist's Owl enters the battlefield, look at the top four cards of your library.
        // You may reveal an artifact or enchantment card from among them and put it into your hand.
        // Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(4, 1, filter, PutCards.HAND, PutCards.BOTTOM_RANDOM)));
    }

    private ArcanistsOwl(final ArcanistsOwl card) {
        super(card);
    }

    @Override
    public ArcanistsOwl copy() {
        return new ArcanistsOwl(this);
    }
}
