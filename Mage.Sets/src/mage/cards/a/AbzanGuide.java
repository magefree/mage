
package mage.cards.a;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class AbzanGuide extends CardImpl {

    public AbzanGuide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{B}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());
        // Morph {2}{W}{B}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<ManaCost>("{2}{W}{B}{G}")));
    }

    private AbzanGuide(final AbzanGuide card) {
        super(card);
    }

    @Override
    public AbzanGuide copy() {
        return new AbzanGuide(this);
    }
}
