package mage.cards.n;

import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class NaturalEmergence extends CardImpl {

    static final private FilterControlledEnchantmentPermanent filter
            = new FilterControlledEnchantmentPermanent("red or green enchantment you control");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.RED), new ColorPredicate(ObjectColor.GREEN)));
    }

    public NaturalEmergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{G}");

        // When Natural Emergence enters the battlefield, return a red or green enchantment you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter), false));

        // Lands you control are 2/2 creatures with first strike. They're still lands.
        this.addAbility(new SimpleStaticAbility(new BecomesCreatureAllEffect(
                new CreatureToken(
                        2, 2, "2/2 creatures with first strike"
                ).withAbility(FirstStrikeAbility.getInstance()), "lands",
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS, Duration.WhileOnBattlefield, false
        )));
    }

    private NaturalEmergence(final NaturalEmergence card) {
        super(card);
    }

    @Override
    public NaturalEmergence copy() {
        return new NaturalEmergence(this);
    }
}
