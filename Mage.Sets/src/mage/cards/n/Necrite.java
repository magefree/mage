package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class Necrite extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature defending player controls");

    public Necrite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.THRULL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Necrite attacks and isn't blocked, you may sacrifice it. If you do, destroy target creature defending player controls. It can't be regenerated.
        DoIfCostPaid effect = new DoIfCostPaid(new DestroyTargetEffect(true), new SacrificeSourceCost(), "Sacrifice {this} to destroy target creature defending player controls?");
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(effect, false, SetTargetPointer.PLAYER);
        ability.addTarget(new TargetPermanent(filter));
        ability.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability);
    }

    private Necrite(final Necrite card) {
        super(card);
    }

    @Override
    public Necrite copy() {
        return new Necrite(this);
    }
}
