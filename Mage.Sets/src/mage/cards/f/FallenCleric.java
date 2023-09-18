
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;

/**
 *
 * @author LoneFox
 */
public final class FallenCleric extends CardImpl {

    private static final FilterCard filter = new FilterCard("Clerics");

    static {
        filter.add(SubType.CLERIC.getPredicate());
    }

    public FallenCleric(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Protection from Clerics
        this.addAbility(new ProtectionAbility(filter));
        // Morph {4}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{4}{B}")));
    }

    private FallenCleric(final FallenCleric card) {
        super(card);
    }

    @Override
    public FallenCleric copy() {
        return new FallenCleric(this);
    }
}
