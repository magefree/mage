
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ImproviseAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class SweatworksBrawler extends CardImpl {

    public SweatworksBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Improvise <i>(Your artifacts can help cast this spell. Each artifact you tap after you're done activating mana abilities pays for {1}.)
        addAbility(new ImproviseAbility());

        // Menace
        this.addAbility(new MenaceAbility(false));
    }

    private SweatworksBrawler(final SweatworksBrawler card) {
        super(card);
    }

    @Override
    public SweatworksBrawler copy() {
        return new SweatworksBrawler(this);
    }
}
