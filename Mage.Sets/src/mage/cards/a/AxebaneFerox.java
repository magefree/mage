package mage.cards.a;

import mage.MageInt;
import mage.abilities.costs.common.CollectEvidenceCost;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AxebaneFerox extends CardImpl {

    public AxebaneFerox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Ward--Collect evidence 4.
        this.addAbility(new WardAbility(new CollectEvidenceCost(4)));
    }

    private AxebaneFerox(final AxebaneFerox card) {
        super(card);
    }

    @Override
    public AxebaneFerox copy() {
        return new AxebaneFerox(this);
    }
}
