package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
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

        // When Arcanist's Owl enters the battlefield, look at the top four cards of your library. You may reveal an artifact or enchantment card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(
                        StaticValue.get(4), false, StaticValue.get(1), filter,
                        Zone.LIBRARY, false, true, false, Zone.HAND,
                        true, false, false
                ).setBackInRandomOrder(true).setText("look at the top four cards of your library. " +
                        "You may reveal an artifact or enchantment card from among them and put it into your hand. " +
                        "Put the rest on the bottom of your library in a random order.")
        ));
    }

    private ArcanistsOwl(final ArcanistsOwl card) {
        super(card);
    }

    @Override
    public ArcanistsOwl copy() {
        return new ArcanistsOwl(this);
    }
}
