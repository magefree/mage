package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author muz
 */
public final class DataExploringHumanity extends CardImpl {

    public DataExploringHumanity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANDROID);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Seek out -- Whenever you draw your second card each turn, discover 4.
        this.addAbility(new DrawNthCardTriggeredAbility(new DiscoverEffect(4)).withFlavorWord("Seek out"));
    }

    private DataExploringHumanity(final DataExploringHumanity card) {
        super(card);
    }

    @Override
    public DataExploringHumanity copy() {
        return new DataExploringHumanity(this);
    }
}
