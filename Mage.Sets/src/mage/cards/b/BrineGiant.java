package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AffinityEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrineGiant extends CardImpl {

    static final FilterControlledPermanent filter = new FilterControlledEnchantmentPermanent("enchantments");
    private static final Hint hint = new ValueHint("Enchantments you control", new PermanentsOnBattlefieldCount(filter));

    public BrineGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // This spell costs {1} less to cast for each enchantment you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AffinityEffect(filter)).addHint(hint));
    }

    private BrineGiant(final BrineGiant card) {
        super(card);
    }

    @Override
    public BrineGiant copy() {
        return new BrineGiant(this);
    }
}
