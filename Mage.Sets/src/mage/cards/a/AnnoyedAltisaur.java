package mage.cards.a;

import mage.MageInt;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AnnoyedAltisaur extends CardImpl {

    public AnnoyedAltisaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cascade
        this.addAbility(new CascadeAbility());
    }

    private AnnoyedAltisaur(final AnnoyedAltisaur card) {
        super(card);
    }

    @Override
    public AnnoyedAltisaur copy() {
        return new AnnoyedAltisaur(this);
    }
}
