package mage.cards.v;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VizkopaVampire extends CardImpl {

    public VizkopaVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W/B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
    }

    private VizkopaVampire(final VizkopaVampire card) {
        super(card);
    }

    @Override
    public VizkopaVampire copy() {
        return new VizkopaVampire(this);
    }
}
