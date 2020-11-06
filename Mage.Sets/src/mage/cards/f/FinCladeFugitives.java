package mage.cards.f;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DauntAbility;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FinCladeFugitives extends CardImpl {

    public FinCladeFugitives(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SALAMANDER);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        // Fin-Clade Fugitives can't be blocked by creatures with power 2 or less.
        this.addAbility(new DauntAbility());

        // Encore {4}{G}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{4}{G}")));
    }

    private FinCladeFugitives(final FinCladeFugitives card) {
        super(card);
    }

    @Override
    public FinCladeFugitives copy() {
        return new FinCladeFugitives(this);
    }
}
