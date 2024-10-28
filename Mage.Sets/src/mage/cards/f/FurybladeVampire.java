package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FurybladeVampire extends CardImpl {

    public FurybladeVampire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // At the beginning of combat on your turn, you may discard a card. If you do, Furyblade Vampire gets +3/+0 until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new DoIfCostPaid(new BoostSourceEffect(3, 0, Duration.EndOfTurn), new DiscardCardCost(), "Discard a card for {this} to get +3/+0 until end of turn?", true), TargetController.YOU, false);
        this.addAbility(ability);
    }

    private FurybladeVampire(final FurybladeVampire card) {
        super(card);
    }

    @Override
    public FurybladeVampire copy() {
        return new FurybladeVampire(this);
    }
}
