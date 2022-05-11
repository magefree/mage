package mage.cards.s;

import mage.MageInt;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SecurityRhox extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.TREASURE, "");

    public SecurityRhox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // You may pay {R}{G} rather than pay this spell's mana cost. Spend only mana produced by Treasures to cast it this way.
        ManaCost cost = new ManaCostsImpl<>("{R}{G}");
        cost.setSourceFilter(filter);
        this.addAbility(new AlternativeCostSourceAbility(
                cost, null, "You may pay {R}{G} rather than pay this spell's mana cost. " +
                "Spend only mana produced by Treasures to cast it this way."
        ));
    }

    private SecurityRhox(final SecurityRhox card) {
        super(card);
    }

    @Override
    public SecurityRhox copy() {
        return new SecurityRhox(this);
    }
}
