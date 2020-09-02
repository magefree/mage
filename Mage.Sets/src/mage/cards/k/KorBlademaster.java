package mage.cards.k;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.EquippedPredicate;

/**
 * @author TheElk801
 */
public final class KorBlademaster extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.WARRIOR, "Equipped Warriors you control");

    static {
        filter.add(EquippedPredicate.instance);
    }

    public KorBlademaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Equipped Warriors you control have double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter
        )));
    }

    private KorBlademaster(final KorBlademaster card) {
        super(card);
    }

    @Override
    public KorBlademaster copy() {
        return new KorBlademaster(this);
    }
}
