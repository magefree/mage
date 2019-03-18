
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class Helvault extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you don't control");

    static {
        filter.add(new ControllerPredicate(TargetController.NOT_YOU));
    }

    public Helvault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        addSuperType(SuperType.LEGENDARY);

        // {1}, {tap}: Exile target creature you control.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
        // {7}, {tap}: Exile target creature you don't control.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect(), new GenericManaCost(7));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
        // When Helvault is put into a graveyard from the battlefield, return all cards exiled with it to the battlefield under their owners' control.
        this.addAbility(new DiesTriggeredAbility(new ReturnFromExileForSourceEffect(Zone.BATTLEFIELD)));
    }

    public Helvault(final Helvault card) {
        super(card);
    }

    @Override
    public Helvault copy() {
        return new Helvault(this);
    }
}
