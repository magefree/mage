package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AkoumWarrior extends CardImpl {

    public AkoumWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}");

        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        this.modalDFC = true;
        this.secondSideCardClazz = mage.cards.a.AkoumTeeth.class;

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private AkoumWarrior(final AkoumWarrior card) {
        super(card);
    }

    @Override
    public AkoumWarrior copy() {
        return new AkoumWarrior(this);
    }
}
