package mage.cards.l;

import mage.MageInt;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.NightboundAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LeechingLurker extends CardImpl {

    public LeechingLurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.LEECH);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);
        this.nightCard = true;

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private LeechingLurker(final LeechingLurker card) {
        super(card);
    }

    @Override
    public LeechingLurker copy() {
        return new LeechingLurker(this);
    }
}
