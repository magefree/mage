package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author vereena42
 */
public final class CoalhaulerSwine extends CardImpl {

    public CoalhaulerSwine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Coalhauler Swine is dealt damage, it deals that much damage to each player.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new DamagePlayersEffect(
                Outcome.Neutral, SavedDamageValue.instance, TargetController.ANY, "it"
        ), false, false));
    }

    private CoalhaulerSwine(final CoalhaulerSwine card) {
        super(card);
    }

    @Override
    public CoalhaulerSwine copy() {
        return new CoalhaulerSwine(this);
    }
}
