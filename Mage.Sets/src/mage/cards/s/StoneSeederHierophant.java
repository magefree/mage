
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Wehk
 */
public final class StoneSeederHierophant extends CardImpl {

    public StoneSeederHierophant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a land enters the battlefield under your control, untap Stone-Seeder Hierophant.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new UntapSourceEffect(), new FilterControlledLandPermanent("a land"), false, null, true));

        // {tap}: Untap target land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new TapSourceCost());
        Target target = new TargetLandPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private StoneSeederHierophant(final StoneSeederHierophant card) {
        super(card);
    }

    @Override
    public StoneSeederHierophant copy() {
        return new StoneSeederHierophant(this);
    }
}
