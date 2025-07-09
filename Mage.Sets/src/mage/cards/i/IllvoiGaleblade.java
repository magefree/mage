package mage.cards.i;

import mage.MageInt;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.token.ClueAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IllvoiGaleblade extends CardImpl {

    public IllvoiGaleblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.JELLYFISH);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}, Sacrifice this creature: Draw a card.
        this.addAbility(new ClueAbility(true));
    }

    private IllvoiGaleblade(final IllvoiGaleblade card) {
        super(card);
    }

    @Override
    public IllvoiGaleblade copy() {
        return new IllvoiGaleblade(this);
    }
}
