
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class DirgurNemesis extends CardImpl {

    public DirgurNemesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Megamorph {6}{U}
        this.addAbility(new MorphAbility(new ManaCostsImpl("{6}{U}"), true));
    }

    private DirgurNemesis(final DirgurNemesis card) {
        super(card);
    }

    @Override
    public DirgurNemesis copy() {
        return new DirgurNemesis(this);
    }
}
