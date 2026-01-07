package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.keyword.BlightControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChaosSpewer extends CardImpl {

    public ChaosSpewer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // When this creature enters, you may pay {2}. If you don't, blight 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoIfCostPaid(
                null, new BlightControllerEffect(2), new GenericManaCost(2)
        )));
    }

    private ChaosSpewer(final ChaosSpewer card) {
        super(card);
    }

    @Override
    public ChaosSpewer copy() {
        return new ChaosSpewer(this);
    }
}
