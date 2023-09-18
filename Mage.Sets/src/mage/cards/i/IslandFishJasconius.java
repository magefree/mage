
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class IslandFishJasconius extends CardImpl {

    public IslandFishJasconius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}{U}");
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(6);
        this.toughness = new MageInt(8);

        // Island Fish Jasconius doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepSourceEffect()));
        
        // At the beginning of your upkeep, you may pay {U}{U}{U}. If you do, untap Island Fish Jasconius.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.BATTLEFIELD,
                new DoIfCostPaid(new UntapSourceEffect(), new ManaCostsImpl<>("{U}{U}{U}")),
                TargetController.YOU,
                false));
        
        // Island Fish Jasconius can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent(SubType.ISLAND, "an Island"))));
        
        // When you control no Islands, sacrifice Island Fish Jasconius.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                new FilterLandPermanent(SubType.ISLAND, "no Islands"), ComparisonType.EQUAL_TO, 0,
                new SacrificeSourceEffect()));
    }

    private IslandFishJasconius(final IslandFishJasconius card) {
        super(card);
    }

    @Override
    public IslandFishJasconius copy() {
        return new IslandFishJasconius(this);
    }
}
