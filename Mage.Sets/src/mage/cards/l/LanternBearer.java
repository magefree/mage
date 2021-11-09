package mage.cards.l;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LanternBearer extends CardImpl {

    public LanternBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.l.LanternsLift.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Disturb {2}{U}
        this.addAbility(new DisturbAbility(this, "{2}{U}"));
    }

    private LanternBearer(final LanternBearer card) {
        super(card);
    }

    @Override
    public LanternBearer copy() {
        return new LanternBearer(this);
    }
}
