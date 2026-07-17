package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AgentOfAtlas extends CardImpl {

    public AgentOfAtlas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private AgentOfAtlas(final AgentOfAtlas card) {
        super(card);
    }

    @Override
    public AgentOfAtlas copy() {
        return new AgentOfAtlas(this);
    }
}
