
package mage.cards.b;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.PreventDamageByChosenSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSource;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BurrentonForgeTender extends CardImpl {

    private static final FilterSource filter = new FilterSource("a red source");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public BurrentonForgeTender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.KITHKIN, SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));

        // Sacrifice Burrenton Forge-Tender: Prevent all damage a red source of your choice would deal this turn.
        this.addAbility(new SimpleActivatedAbility(new PreventDamageByChosenSourceEffect(filter), new SacrificeSourceCost()));

    }

    private BurrentonForgeTender(final BurrentonForgeTender card) {
        super(card);
    }

    @Override
    public BurrentonForgeTender copy() {
        return new BurrentonForgeTender(this);
    }
}
