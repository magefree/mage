package mage.cards.z;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MiracleAbility;
import mage.abilities.keyword.SquadAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Zephyrim extends CardImpl {

    public Zephyrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Squad {2}
        this.addAbility(new SquadAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Miracle {1}{W}
        this.addAbility(new MiracleAbility("{1}{W}"));
    }

    private Zephyrim(final Zephyrim card) {
        super(card);
    }

    @Override
    public Zephyrim copy() {
        return new Zephyrim(this);
    }
}
