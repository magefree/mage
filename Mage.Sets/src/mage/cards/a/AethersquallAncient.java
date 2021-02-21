
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.ReturnToHandFromBattlefieldAllEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author fireshoes
 */
public final class AethersquallAncient extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AethersquallAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, you get {E}{E}{E}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GetEnergyCountersControllerEffect(3), TargetController.YOU, false));

        // Pay {E}{E}{E}{E}{E}{E}{E}{E}: Return all other creatures to their owners' hands. Activate this ability only any time you could cast a sorcery.
        this.addAbility(new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandFromBattlefieldAllEffect(filter), new PayEnergyCost(8)));
    }

    private AethersquallAncient(final AethersquallAncient card) {
        super(card);
    }

    @Override
    public AethersquallAncient copy() {
        return new AethersquallAncient(this);
    }
}
