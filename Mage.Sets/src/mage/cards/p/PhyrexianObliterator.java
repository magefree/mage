package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SourceDealsDamageToThisTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class PhyrexianObliterator extends CardImpl {

    public PhyrexianObliterator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{B}{B}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a source deals damage to Phyrexian Obliterator, that source's controller sacrifices that many permanents.
        this.addAbility(new SourceDealsDamageToThisTriggeredAbility(
                new SacrificeEffect(StaticFilters.FILTER_PERMANENTS, SavedDamageValue.MANY, "that source's controller")
        ));
    }

    private PhyrexianObliterator(final PhyrexianObliterator card) {
        super(card);
    }

    @Override
    public PhyrexianObliterator copy() {
        return new PhyrexianObliterator(this);
    }
}
