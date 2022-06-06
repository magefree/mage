
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
public final class FoothillGuide extends CardImpl {

    private static final FilterCard filter = new FilterCard("Goblins");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
    }

    public FoothillGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from Goblins
        this.addAbility(new ProtectionAbility(filter));
        // Morph {W}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{W}")));
    }

    private FoothillGuide(final FoothillGuide card) {
        super(card);
    }

    @Override
    public FoothillGuide copy() {
        return new FoothillGuide(this);
    }
}
