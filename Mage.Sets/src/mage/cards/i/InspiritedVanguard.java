package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.keyword.EndureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InspiritedVanguard extends CardImpl {

    public InspiritedVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever this creature enters or attacks, it endures 2.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new EndureSourceEffect(2)));
    }

    private InspiritedVanguard(final InspiritedVanguard card) {
        super(card);
    }

    @Override
    public InspiritedVanguard copy() {
        return new InspiritedVanguard(this);
    }
}
