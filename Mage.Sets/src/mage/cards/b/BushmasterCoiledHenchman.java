package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class BushmasterCoiledHenchman extends CardImpl {

    public BushmasterCoiledHenchman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Each other creature you control with a +1/+1 counter on it has deathtouch.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
            DeathtouchAbility.getInstance(),
            Duration.WhileOnBattlefield,
            StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE_P1P1,
            "Each other creature you control with a +1/+1 counter on it has deathtouch."
        )));
    }

    private BushmasterCoiledHenchman(final BushmasterCoiledHenchman card) {
        super(card);
    }

    @Override
    public BushmasterCoiledHenchman copy() {
        return new BushmasterCoiledHenchman(this);
    }
}
