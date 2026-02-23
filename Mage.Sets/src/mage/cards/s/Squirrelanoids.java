package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Squirrelanoids extends CardImpl {

    public Squirrelanoids(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());
    }

    private Squirrelanoids(final Squirrelanoids card) {
        super(card);
    }

    @Override
    public Squirrelanoids copy() {
        return new Squirrelanoids(this);
    }
}
