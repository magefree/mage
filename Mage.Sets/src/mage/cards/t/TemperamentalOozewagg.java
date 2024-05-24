package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.AdaptAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TemperamentalOozewagg extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("modified creatures");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public TemperamentalOozewagg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.OOZE);
        this.subtype.add(SubType.BRUSHWAGG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {2}{G}: Adapt 2.
        this.addAbility(new AdaptAbility(2, "{2}{G}"));

        // Modified creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private TemperamentalOozewagg(final TemperamentalOozewagg card) {
        super(card);
    }

    @Override
    public TemperamentalOozewagg copy() {
        return new TemperamentalOozewagg(this);
    }
}
