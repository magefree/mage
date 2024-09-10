package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LazotepReaver extends CardImpl {

    public LazotepReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Lazotep Reaver enters the battlefield, amass 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AmassEffect(1, SubType.ZOMBIE)));
    }

    private LazotepReaver(final LazotepReaver card) {
        super(card);
    }

    @Override
    public LazotepReaver copy() {
        return new LazotepReaver(this);
    }
}
