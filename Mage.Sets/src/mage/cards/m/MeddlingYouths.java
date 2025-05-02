package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeddlingYouths extends CardImpl {

    public MeddlingYouths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you attack with three or more creatures, investigate.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new InvestigateEffect(), 3));
    }

    private MeddlingYouths(final MeddlingYouths card) {
        super(card);
    }

    @Override
    public MeddlingYouths copy() {
        return new MeddlingYouths(this);
    }
}
