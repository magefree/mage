package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Styxo
 */
public final class AdroitHateflayer extends CardImpl {

    public AdroitHateflayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}");
        this.subtype.add(SubType.NAUTOLAN);
        this.subtype.add(SubType.SITH);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Adroit Hateflayer attacks, each player loses 2 life.
        this.addAbility(new AttacksTriggeredAbility(new LoseLifeAllPlayersEffect(2), false));
    }

    private AdroitHateflayer(final AdroitHateflayer card) {
        super(card);
    }

    @Override
    public AdroitHateflayer copy() {
        return new AdroitHateflayer(this);
    }
}
