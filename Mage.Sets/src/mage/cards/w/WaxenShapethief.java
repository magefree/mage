package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WaxenShapethief extends CardImpl {

    public WaxenShapethief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // You may have this creature enter as a copy of a creature or artifact you control.
        this.addAbility(new EntersBattlefieldAbility(
                new CopyPermanentEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE), true
        ));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private WaxenShapethief(final WaxenShapethief card) {
        super(card);
    }

    @Override
    public WaxenShapethief copy() {
        return new WaxenShapethief(this);
    }
}
