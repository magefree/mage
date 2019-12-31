package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostSourceWhileControlsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Shinechaser extends CardImpl {

    private static final FilterPermanent filter = new FilterEnchantmentPermanent("an enchantment");

    public Shinechaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{U}");

        this.subtype.add(SubType.FAERIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Shinechaser gets +1/+1 as long as you control an artifact.
        this.addAbility(new SimpleStaticAbility(
                new BoostSourceWhileControlsEffect(StaticFilters.FILTER_PERMANENT_ARTIFACT_AN, 1, 1)
        ));

        // Shinechaser gets +1/+1 as long as you control an enchantment.
        this.addAbility(new SimpleStaticAbility(new BoostSourceWhileControlsEffect(filter, 1, 1)));
    }

    private Shinechaser(final Shinechaser card) {
        super(card);
    }

    @Override
    public Shinechaser copy() {
        return new Shinechaser(this);
    }
}
