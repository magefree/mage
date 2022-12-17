package mage.cards.o;

import mage.MageInt;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class Orbak extends CardImpl {
    public Orbak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.addSubType(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        //{4}{W}: Monstrosity 3
        this.addAbility(new MonstrosityAbility("{4}{W}", 3));
    }

    public Orbak(final Orbak card) {
        super(card);
    }

    @Override
    public Orbak copy() {
        return new Orbak(this);
    }
}
