package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

public class ForsakenThresher extends CardImpl {

    public ForsakenThresher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Back half of Foreboding Statue
        this.nightCard = true;

        // At the beginning of your precombat main phase, add one mana of any color.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(new AddManaOfAnyColorEffect(), TargetController.YOU, false));
    }

    private ForsakenThresher(final ForsakenThresher card) {
        super(card);
    }

    @Override
    public ForsakenThresher copy() {
        return new ForsakenThresher(this);
    }
}
