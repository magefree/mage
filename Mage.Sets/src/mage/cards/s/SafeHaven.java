package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class SafeHaven extends CardImpl {

    public SafeHaven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {2}, {tap}: Exile target creature you control.
        Ability ability = new SimpleActivatedAbility(new ExileTargetForSourceEffect(), new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // At the beginning of your upkeep, you may sacrifice Safe Haven. If you do, return each card exiled with Safe Haven to the battlefield under its owner's control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(new ReturnFromExileEffect(
                        Zone.BATTLEFIELD, "return each card exiled with " +
                        "{this} to the battlefield under its owner's control"
                ), new SacrificeSourceCost()), TargetController.YOU, false
        ));
    }

    private SafeHaven(final SafeHaven card) {
        super(card);
    }

    @Override
    public SafeHaven copy() {
        return new SafeHaven(this);
    }
}
