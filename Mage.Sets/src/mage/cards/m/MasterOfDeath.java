package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MasterOfDeath extends CardImpl {

    public MasterOfDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Master of Death enters the battlefield, surveil 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(2)));

        // At the beginning of your upkeep, if Master of Death is in your graveyard, you may pay 1 life. If you do, return it to your hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                Zone.GRAVEYARD,
                new DoIfCostPaid(
                        new ReturnSourceFromGraveyardToHandEffect()
                                .setText("return it to your hand"),
                        new PayLifeCost(1)
                ), TargetController.YOU, false
        ));
    }

    private MasterOfDeath(final MasterOfDeath card) {
        super(card);
    }

    @Override
    public MasterOfDeath copy() {
        return new MasterOfDeath(this);
    }
}
