
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ScavengeAbility;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SewerShambler extends CardImpl {

    public SewerShambler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Swampwalk (This creature can't be blocked as long as defending player controls a Swamp.)
        this.addAbility(new SwampwalkAbility());
        // Scavenge {2}{B} ({2}{B}, Exile this card from your graveyard: Put a number of +1/+1 counters equal to this card's power on target creature. Scavenge only as a sorcery.)
        this.addAbility(new ScavengeAbility(new ManaCostsImpl<>("{2}{B}")));
    }

    private SewerShambler(final SewerShambler card) {
        super(card);
    }

    @Override
    public SewerShambler copy() {
        return new SewerShambler(this);
    }
}
