package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.ExpendTriggeredAbility;
import mage.abilities.costs.common.DiscardHandCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BywayBarterer extends CardImpl {

    public BywayBarterer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.RACCOON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever you expend 4, you may discard your hand. If you do, draw two cards.
        this.addAbility(new ExpendTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DiscardHandCost()
        ), ExpendTriggeredAbility.Expend.FOUR));
    }

    private BywayBarterer(final BywayBarterer card) {
        super(card);
    }

    @Override
    public BywayBarterer copy() {
        return new BywayBarterer(this);
    }
}
