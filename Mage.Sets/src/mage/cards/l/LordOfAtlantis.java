
package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class LordOfAtlantis extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.MERFOLK);

    public LordOfAtlantis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");
        this.subtype.add(SubType.MERFOLK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Merfolk get +1/+1 and have islandwalk.
        Ability ability = new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityAllEffect(
                new IslandwalkAbility(), Duration.WhileOnBattlefield, filter, true
        ).setText("and have islandwalk"));
        this.addAbility(ability);
    }

    private LordOfAtlantis(final LordOfAtlantis card) {
        super(card);
    }

    @Override
    public LordOfAtlantis copy() {
        return new LordOfAtlantis(this);
    }
}
