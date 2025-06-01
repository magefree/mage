package mage.cards.a;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArborAdherent extends CardImpl {

    public ArborAdherent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DOG);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {T}: Add X mana of any one color, where X is the greatest toughness among other creatures you control.
        this.addAbility(new DynamicManaAbility(
                Mana.AnyMana(1), GreatestAmongPermanentsValue.TOUGHNESS_OTHER_CONTROLLED_CREATURES,
                new TapSourceCost(), "add X mana of any one color, where X is the " +
                "greatest toughness among other creatures you control", true
        ).addHint(GreatestAmongPermanentsValue.TOUGHNESS_OTHER_CONTROLLED_CREATURES.getHint()));
    }

    private ArborAdherent(final ArborAdherent card) {
        super(card);
    }

    @Override
    public ArborAdherent copy() {
        return new ArborAdherent(this);
    }
}
