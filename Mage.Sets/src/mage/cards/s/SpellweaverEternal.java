
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.AfflictAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class SpellweaverEternal extends CardImpl {

    public SpellweaverEternal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Prowess
        this.addAbility(new ProwessAbility());

        // Afflict 2
        this.addAbility(new AfflictAbility(2));
    }

    private SpellweaverEternal(final SpellweaverEternal card) {
        super(card);
    }

    @Override
    public SpellweaverEternal copy() {
        return new SpellweaverEternal(this);
    }
}
