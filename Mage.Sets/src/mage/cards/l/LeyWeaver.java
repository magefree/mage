
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author TheElk801
 */
public final class LeyWeaver extends CardImpl {

    public LeyWeaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Lore Weaver (When this creature enters the battlefield, target player may put Lore Weaver into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Lore Weaver"));

        // {T}: Untap two target lands.
        Ability ability = new SimpleActivatedAbility(new UntapTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent(2));
        this.addAbility(ability);
    }

    private LeyWeaver(final LeyWeaver card) {
        super(card);
    }

    @Override
    public LeyWeaver copy() {
        return new LeyWeaver(this);
    }
}
