package mage.cards.z;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZhaoTheSeethingFlame extends CardImpl {

    public ZhaoTheSeethingFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private ZhaoTheSeethingFlame(final ZhaoTheSeethingFlame card) {
        super(card);
    }

    @Override
    public ZhaoTheSeethingFlame copy() {
        return new ZhaoTheSeethingFlame(this);
    }
}
