
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author anonymous
 */
public final class ScreechingSliver extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SLIVER, "All Slivers");

    public ScreechingSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Slivers have "{T}: Target player puts the top card of their library into their graveyard."
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutLibraryIntoGraveTargetEffect(1), new TapSourceCost());
        ability.addTarget(new TargetPlayer());

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new GainAbilityAllEffect(ability, Duration.WhileOnBattlefield,
                        filter, "All Slivers have \"{T}: Target player puts the top card of their library into their graveyard.\"")));
    }

    public ScreechingSliver(final ScreechingSliver card) {
        super(card);
    }

    @Override
    public ScreechingSliver copy() {
        return new ScreechingSliver(this);
    }
}
