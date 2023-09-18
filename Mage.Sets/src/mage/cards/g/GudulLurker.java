
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class GudulLurker extends CardImpl {

    public GudulLurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.SALAMANDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Gudul Lurker can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Megamorph {U}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{U}"), true));
    }

    private GudulLurker(final GudulLurker card) {
        super(card);
    }

    @Override
    public GudulLurker copy() {
        return new GudulLurker(this);
    }
}
