package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.CycleControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrannithStinger extends CardImpl {

    public DrannithStinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cycle another card, Drannith Stinger deals 1 damage to each opponent.
        this.addAbility(new CycleControllerTriggeredAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), false, true
        ));

        // Cycling {1}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}")));
    }

    private DrannithStinger(final DrannithStinger card) {
        super(card);
    }

    @Override
    public DrannithStinger copy() {
        return new DrannithStinger(this);
    }
}
