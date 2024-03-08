package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class TarPitcher extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.GOBLIN, "Goblin");

    public TarPitcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        // {tap}, Sacrifice a Goblin: Tar Pitcher deals 2 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(2), new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private TarPitcher(final TarPitcher card) {
        super(card);
    }

    @Override
    public TarPitcher copy() {
        return new TarPitcher(this);
    }
}
