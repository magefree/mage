package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantmentOrEnchantedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TempleThief extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("enchanted creatures or enchantment creatures");

    static {
        filter.add(EnchantmentOrEnchantedPredicate.instance);
    }

    public TempleThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Temple Thief can't be blocked by enchanted creatures or enchantment creatures.
        this.addAbility(new SimpleStaticAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.WhileOnBattlefield)
        ));
    }

    private TempleThief(final TempleThief card) {
        super(card);
    }

    @Override
    public TempleThief copy() {
        return new TempleThief(this);
    }
}
