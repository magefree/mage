package mage.cards.w;

import mage.MageInt;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class WaningWurm extends CardImpl {

    public WaningWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WURM);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Vanishing 2
        this.addAbility(new VanishingAbility(2));
    }

    private WaningWurm(final WaningWurm card) {
        super(card);
    }

    @Override
    public WaningWurm copy() {
        return new WaningWurm(this);
    }
}
