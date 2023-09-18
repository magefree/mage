
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author North, noxx
 */
public final class KabiraVindicator extends LevelerCard {

    public KabiraVindicator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{2}{W}")));

        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, true)));

        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(2, 2, Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES, true)));

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(2, 4, abilities1, 3, 6),
                new LevelerCardBuilder.LevelAbility(5, -1, abilities2, 4, 8)
        ));
        setMaxLevelCounters(5);
    }

    private KabiraVindicator(final KabiraVindicator card) {
        super(card);
    }

    @Override
    public KabiraVindicator copy() {
        return new KabiraVindicator(this);
    }
}
