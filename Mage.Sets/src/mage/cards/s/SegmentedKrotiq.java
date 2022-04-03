
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SegmentedKrotiq extends CardImpl {

    public SegmentedKrotiq(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Megamorph {6}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{6}{G}"), true));
    }

    private SegmentedKrotiq(final SegmentedKrotiq card) {
        super(card);
    }

    @Override
    public SegmentedKrotiq copy() {
        return new SegmentedKrotiq(this);
    }
}
