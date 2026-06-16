package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.keyword.MayhemAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GreenGoblinBackForMore extends CardImpl {

    public GreenGoblinBackForMore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Menace
        this.addAbility(new MenaceAbility());

        // At the beginning of combat on your turn, you may discard a card. If you do, each opponent discards a card.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
            new DoIfCostPaid(new DiscardEachPlayerEffect(TargetController.OPPONENT), new DiscardCardCost())
        ));

        // Mayhem {3}{B}{B}
        this.addAbility(new MayhemAbility(this, "{3}{B}{B}"));
    }

    private GreenGoblinBackForMore(final GreenGoblinBackForMore card) {
        super(card);
    }

    @Override
    public GreenGoblinBackForMore copy() {
        return new GreenGoblinBackForMore(this);
    }
}
