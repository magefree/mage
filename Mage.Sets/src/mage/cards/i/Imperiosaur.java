
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.StaticAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class Imperiosaur extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    public Imperiosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.subtype.add(SubType.DINOSAUR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Spend only mana produced by basic lands to cast Imperiosaur.
        this.addAbility(new ImperiosaurStaticAbility());
        this.getSpellAbility().getManaCostsToPay().setSourceFilter(filter);
        this.getSpellAbility().getManaCosts().setSourceFilter(filter);
    }

    private Imperiosaur(final Imperiosaur card) {
        super(card);
    }

    @Override
    public Imperiosaur copy() {
        return new Imperiosaur(this);
    }
}

class ImperiosaurStaticAbility extends StaticAbility {

    public ImperiosaurStaticAbility() {
        super(Zone.STACK, null);
    }

    private ImperiosaurStaticAbility(final ImperiosaurStaticAbility ability) {
        super(ability);
    }

    @Override
    public ImperiosaurStaticAbility copy() {
        return new ImperiosaurStaticAbility(this);
    }

    @Override
    public String getRule() {
        return "Spend only mana produced by basic lands to cast this spell.";
    }

}
