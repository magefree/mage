
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnLibrarySourceEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class ThalakosMistfolk extends CardImpl {

    public ThalakosMistfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.THALAKOS);
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // {U}: Put Thalakos Mistfolk on top of its owner's library.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutOnLibrarySourceEffect(true), new ManaCostsImpl<>("{U}")));
    }

    private ThalakosMistfolk(final ThalakosMistfolk card) {
        super(card);
    }

    @Override
    public ThalakosMistfolk copy() {
        return new ThalakosMistfolk(this);
    }
}
