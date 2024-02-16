package mage.cards.g;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.DauntAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GreaterSandwurm extends CardImpl {

    public GreaterSandwurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.WURM);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Greater Sandwurm can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private GreaterSandwurm(final GreaterSandwurm card) {
        super(card);
    }

    @Override
    public GreaterSandwurm copy() {
        return new GreaterSandwurm(this);
    }
}
