
package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MaximumDynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 *
 * @author Grath
 */
public final class ProphetOfTheScarab extends CardImpl {
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ZOMBIE);
    private static final FilterCard filter2 = new FilterCard();

    static {
        filter2.add(SubType.ZOMBIE.getPredicate());
    }

    private static final DynamicValue xValue = new MaximumDynamicValue(
            new PermanentsOnBattlefieldCount(filter),
            new CardsInControllerGraveyardCount(filter2)
    );

    public ProphetOfTheScarab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When this creature enters, draw cards equal to the number of Zombies you control or the number of Zombie cards in your graveyard, whichever is greater.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(xValue)
                .setText("draw cards equal to the number of Zombies you control or the number of Zombie cards in your graveyard, whichever is greater"))
                .addHint(new ValueHint("Current draw", xValue)));
    }

    private ProphetOfTheScarab(final ProphetOfTheScarab card) {
        super(card);
    }

    @Override
    public ProphetOfTheScarab copy() {
        return new ProphetOfTheScarab(this);
    }
}
