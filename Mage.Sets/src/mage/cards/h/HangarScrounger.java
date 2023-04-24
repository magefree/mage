package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.BackupAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HangarScrounger extends CardImpl {

    public HangarScrounger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Backup 1
        BackupAbility backupAbility = new BackupAbility(this, 1);
        this.addAbility(backupAbility);

        // Whenever this creature becomes tapped, you may discard a card. If you do, draw a card.
        backupAbility.addAbility(new BecomesTappedSourceTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost())
        ).setTriggerPhrase("Whenever this creature becomes tapped, "));
    }

    private HangarScrounger(final HangarScrounger card) {
        super(card);
    }

    @Override
    public HangarScrounger copy() {
        return new HangarScrounger(this);
    }
}
