
package mage.cards.m;

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
public final class MarshHulk extends CardImpl {

    public MarshHulk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.OGRE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Megamorph {6}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{6}{B}"), true));
    }

    private MarshHulk(final MarshHulk card) {
        super(card);
    }

    @Override
    public MarshHulk copy() {
        return new MarshHulk(this);
    }
}
