package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MonstrosityAbility;
import mage.constants.SubType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author NinthWorld
 */
public final class Fathier extends CardImpl {

    public Fathier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {4}{R}{R}: Monstrosity 2.
        this.addAbility(new MonstrosityAbility("{4}{R}{R}", 2));
    }

    private Fathier(final Fathier card) {
        super(card);
    }

    @Override
    public Fathier copy() {
        return new Fathier(this);
    }
}
