package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.common.PayEnergyCost;
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
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1).setText("you draw a card"), new PayEnergyCost(2)
        ).addEffect(new LoseLifeSourceControllerEffect(1).concatBy("and")), TargetController.YOU, false));
    }

    private GlintSleeveSiphoner(final GlintSleeveSiphoner card) {
        super(card);
    }

    @Override
    public GlintSleeveSiphoner copy() {
        return new GlintSleeveSiphoner(this);
    }
}
