package mage.cards.p;

import mage.MageInt;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PutridGoblin extends CardImpl {

    public PutridGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Persist
        this.addAbility(new PersistAbility());
    }

    private PutridGoblin(final PutridGoblin card) {
        super(card);
    }

    @Override
    public PutridGoblin copy() {
        return new PutridGoblin(this);
    }
}
