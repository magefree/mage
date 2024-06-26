package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AchillesDavenport extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.ASSASSIN, "Assassins");

    public AchillesDavenport(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Freerunning {U}{B}
        this.addAbility(new FreerunningAbility("{U}{B}"));

        // Menace
        this.addAbility(new MenaceAbility());

        // Other Assassins you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        )));
    }

    private AchillesDavenport(final AchillesDavenport card) {
        super(card);
    }

    @Override
    public AchillesDavenport copy() {
        return new AchillesDavenport(this);
    }
}
