package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AttentiveSunscribe extends CardImpl {

    public AttentiveSunscribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Attentive Sunscribe becomes tapped, scry 1.
        this.addAbility(new BecomesTappedSourceTriggeredAbility(new ScryEffect(1)));
    }

    private AttentiveSunscribe(final AttentiveSunscribe card) {
        super(card);
    }

    @Override
    public AttentiveSunscribe copy() {
        return new AttentiveSunscribe(this);
    }
}
