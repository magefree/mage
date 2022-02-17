package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class GlintSleeveSiphoner extends CardImpl {

    public GlintSleeveSiphoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Glint-Sleeve Siphoner enters the battlefield or attacks, you get {E}.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new GetEnergyCountersControllerEffect(1)));

        // At the beginning of your upkeep, you may pay {E}{E}. If you do, draw a card and you lose 1 life.
        DoIfCostPaid doIfCostPaidEffect = new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new PayEnergyCost(2));
        Effect effect = new LoseLifeSourceControllerEffect(1);
        doIfCostPaidEffect.addEffect(effect.concatBy("and"));
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, doIfCostPaidEffect, TargetController.YOU, false, false,
                "At the beginning of your upkeep, "));
    }

    private GlintSleeveSiphoner(final GlintSleeveSiphoner card) {
        super(card);
    }

    @Override
    public GlintSleeveSiphoner copy() {
        return new GlintSleeveSiphoner(this);
    }
}
