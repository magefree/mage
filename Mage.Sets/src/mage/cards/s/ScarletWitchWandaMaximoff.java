package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ScarletWitchWandaMaximoff extends CardImpl {

    public ScarletWitchWandaMaximoff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.WARLOCK);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());
    }

    private ScarletWitchWandaMaximoff(final ScarletWitchWandaMaximoff card) {
        super(card);
    }

    @Override
    public ScarletWitchWandaMaximoff copy() {
        return new ScarletWitchWandaMaximoff(this);
    }
}
