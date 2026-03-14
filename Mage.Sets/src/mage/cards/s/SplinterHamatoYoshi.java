package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SplinterHamatoYoshi extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.NINJA, "Ninjas");

    public SplinterHamatoYoshi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Sneak {B}
        this.addAbility(new SneakAbility(this, "{B}"));

        // Menace
        this.addAbility(new MenaceAbility());

        // Other Ninjas you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private SplinterHamatoYoshi(final SplinterHamatoYoshi card) {
        super(card);
    }

    @Override
    public SplinterHamatoYoshi copy() {
        return new SplinterHamatoYoshi(this);
    }
}
