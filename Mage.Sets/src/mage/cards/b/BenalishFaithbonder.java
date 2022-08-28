package mage.cards.b;

import mage.MageInt;
import mage.abilities.keyword.EnlistAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BenalishFaithbonder extends CardImpl {

    public BenalishFaithbonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Enlist
        this.addAbility(new EnlistAbility());
    }

    private BenalishFaithbonder(final BenalishFaithbonder card) {
        super(card);
    }

    @Override
    public BenalishFaithbonder copy() {
        return new BenalishFaithbonder(this);
    }
}
