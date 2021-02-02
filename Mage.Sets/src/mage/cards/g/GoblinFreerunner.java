
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.SurgeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class GoblinFreerunner extends CardImpl {

    public GoblinFreerunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Surge {1}{R} (You may cast this spell for its surge cost if you or a teammate has cast another spell this turn.)
        addAbility(new SurgeAbility(this, "{1}{R}"));
        
        // Menace
        this.addAbility(new MenaceAbility());
    }

    private GoblinFreerunner(final GoblinFreerunner card) {
        super(card);
    }

    @Override
    public GoblinFreerunner copy() {
        return new GoblinFreerunner(this);
    }
}
