package mage.cards.h;

import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class Helvault extends CardImpl {

    public Helvault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.supertype.add(SuperType.LEGENDARY);

        // {1}, {T}: Exile target creature you control.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // {7}, {T}: Exile target creature you don't control.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect(), new GenericManaCost(7));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);

        // When Helvault is put into a graveyard from the battlefield, return all cards exiled with it to the battlefield under their owners' control.
        this.addAbility(new DiesSourceTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD)));
    }

    private Helvault(final Helvault card) {
        super(card);
    }

    @Override
    public Helvault copy() {
        return new Helvault(this);
    }
}
