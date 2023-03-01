package mage.cards.t;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TyrranaxAtrocity extends CardImpl {

    public TyrranaxAtrocity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Toxic 3
        this.addAbility(new ToxicAbility(3));
    }

    private TyrranaxAtrocity(final TyrranaxAtrocity card) {
        super(card);
    }

    @Override
    public TyrranaxAtrocity copy() {
        return new TyrranaxAtrocity(this);
    }
}
