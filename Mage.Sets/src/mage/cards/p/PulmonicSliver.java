
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ReturnToLibraryPermanentEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author anonymous
 */
public final class PulmonicSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.SLIVER, "All Sliver creatures");

    private static final FilterPermanent filterSlivers = new FilterPermanent(SubType.SLIVER, "All Slivers");

    public PulmonicSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.SLIVER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // All Sliver creatures have flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                FlyingAbility.getInstance(), Duration.WhileOnBattlefield,
                filter, "All Sliver creatures have flying.")));
        // All Slivers have "If this permanent would be put into a graveyard, you may put it on top of its owner's library instead."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(
                new PutIntoGraveFromAnywhereSourceAbility(new ReturnToLibraryPermanentEffect(true), null, null, false, true), Duration.WhileOnBattlefield,
                filterSlivers, "All Slivers have \"If this permanent would be put into a graveyard, you may put it on top of its owner's library instead.\"")));
    }

    private PulmonicSliver(final PulmonicSliver card) {
        super(card);
    }

    @Override
    public PulmonicSliver copy() {
        return new PulmonicSliver(this);
    }
}
