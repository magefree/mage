package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.RenewAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlchemistsAssistant extends CardImpl {

    public AlchemistsAssistant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.MONKEY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Renew -- {1}{B}, Exile this card from your graveyard: Put a lifelink counter on target creature. Activate only as a sorcery.
        this.addAbility(new RenewAbility("{1}{B}", CounterType.LIFELINK.createInstance()));
    }

    private AlchemistsAssistant(final AlchemistsAssistant card) {
        super(card);
    }

    @Override
    public AlchemistsAssistant copy() {
        return new AlchemistsAssistant(this);
    }
}
