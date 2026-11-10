package mage.cards.c;

import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author muz
 */
public final class ClassFShuttlecraft extends CardImpl {

    public ClassFShuttlecraft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.SPACECRAFT);

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // Station
        this.addAbility(new StationAbility());

        // STATION 6+
        // Flying
        // Vigilance
        // 2/4
        this.addAbility(new StationLevelAbility(6)
            .withLevelAbility(FlyingAbility.getInstance())
            .withLevelAbility(VigilanceAbility.getInstance())
            .withPT(2, 4));
    }

    private ClassFShuttlecraft(final ClassFShuttlecraft card) {
        super(card);
    }

    @Override
    public ClassFShuttlecraft copy() {
        return new ClassFShuttlecraft(this);
    }
}
