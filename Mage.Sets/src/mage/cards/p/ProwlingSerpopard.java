
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CantBeCounteredControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterSpell;

/**
 *
 * @author fireshoes
 */
public final class ProwlingSerpopard extends CardImpl {

    private static final FilterSpell filterTarget = new FilterSpell("Creature spells you control");

    static {
        filterTarget.add(CardType.CREATURE.getPredicate());
    }

    public ProwlingSerpopard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Prowling Serpopard can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());

        // Creature spells you control can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeCounteredControlledEffect(filterTarget, null, Duration.WhileOnBattlefield)));
    }

    private ProwlingSerpopard(final ProwlingSerpopard card) {
        super(card);
    }

    @Override
    public ProwlingSerpopard copy() {
        return new ProwlingSerpopard(this);
    }
}
