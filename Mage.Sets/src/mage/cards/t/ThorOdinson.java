package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ThorOdinson extends CardImpl {

    public ThorOdinson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private ThorOdinson(final ThorOdinson card) {
        super(card);
    }

    @Override
    public ThorOdinson copy() {
        return new ThorOdinson(this);
    }
}
