package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuardianAugmenter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("commander creatures");
    private static final FilterPermanent filter2 = new FilterPermanent("commanders");

    static {
        filter.add(CommanderPredicate.instance);
        filter2.add(CommanderPredicate.instance);
    }

    public GuardianAugmenter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.TROLL);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Commander creatures you control get +2/+2.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                2, 2, Duration.WhileOnBattlefield, filter
        )));

        // Commanders you control have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        )));
    }

    private GuardianAugmenter(final GuardianAugmenter card) {
        super(card);
    }

    @Override
    public GuardianAugmenter copy() {
        return new GuardianAugmenter(this);
    }
}
