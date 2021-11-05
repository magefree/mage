package mage.cards.d;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisturbAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrogskolInfantry extends CardImpl {

    public DrogskolInfantry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.d.DrogskolArmaments.class;

        // Disturb {3}{W}
        this.addAbility(new DisturbAbility(new ManaCostsImpl<>("{3}{W}")));
    }

    private DrogskolInfantry(final DrogskolInfantry card) {
        super(card);
    }

    @Override
    public DrogskolInfantry copy() {
        return new DrogskolInfantry(this);
    }
}
