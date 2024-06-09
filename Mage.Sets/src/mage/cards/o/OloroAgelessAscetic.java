
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author LevelX2
 */
public final class OloroAgelessAscetic extends CardImpl {

    public OloroAgelessAscetic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, you gain 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(2), TargetController.YOU, false));

        // Whenever you gain life, you may pay {1}. If you do, draw a card and each opponent loses 1 life.
        DoIfCostPaid effect = new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        Effect effectToAdd = new LoseLifeOpponentsEffect(1);
        effectToAdd.setText("and each opponent loses 1 life");
        effect.addEffect(effectToAdd);
        this.addAbility(new GainLifeControllerTriggeredAbility(effect, false));

        // At the beginning of your upkeep, if Oloro, Ageless Ascetic is in the command zone, you gain 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.COMMAND,
                new GainLifeEffect(StaticValue.get(2), "if {this} is in the command zone, you gain 2 life"), TargetController.YOU, false));
    }

    private OloroAgelessAscetic(final OloroAgelessAscetic card) {
        super(card);
    }

    @Override
    public OloroAgelessAscetic copy() {
        return new OloroAgelessAscetic(this);
    }
}
