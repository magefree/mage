package mage.cards.p;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.keyword.DauntAbility;
import mage.constants.SubType;
import mage.abilities.keyword.ToxicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author TheElk801
 */
public final class PaladinOfPredation extends CardImpl {

    public PaladinOfPredation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Toxic 6
        this.addAbility(new ToxicAbility(6));

        // Paladin of Predation can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());
    }

    private PaladinOfPredation(final PaladinOfPredation card) {
        super(card);
    }

    @Override
    public PaladinOfPredation copy() {
        return new PaladinOfPredation(this);
    }
}
