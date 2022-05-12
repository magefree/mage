package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class CryptSliver extends CardImpl {

    private static final FilterPermanent filter=new FilterPermanent(SubType.SLIVER,"Sliver");
    public CryptSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // All Slivers have "{tap}: Regenerate target Sliver."
        Ability ability = new SimpleActivatedAbility( new RegenerateTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new SimpleStaticAbility( new GainAbilityAllEffect(
                ability, Duration.WhileOnBattlefield, filter
        ).setText("all Slivers have \"{T}: Regenerate target Sliver.\"")));
    }

    private CryptSliver(final CryptSliver card) {
        super(card);
    }

    @Override
    public CryptSliver copy() {
        return new CryptSliver(this);
    }
}
