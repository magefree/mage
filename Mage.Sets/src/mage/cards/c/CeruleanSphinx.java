
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox

 */
public final class CeruleanSphinx extends CardImpl {

    public CeruleanSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {U}: Cerulean Sphinx's owner shuffles it into their library.
        Effect effect = new ShuffleIntoLibrarySourceEffect();
        effect.setText("{this}'s owner shuffles it into their library.");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{U}")));
    }

    private CeruleanSphinx(final CeruleanSphinx card) {
        super(card);
    }

    @Override
    public CeruleanSphinx copy() {
        return new CeruleanSphinx(this);
    }
}
