

package mage.cards.i;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author Loki
 */
public final class InkmothNexus extends CardImpl {

    public InkmothNexus (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}: Inkmoth Nexus becomes a 1/1 Phyrexian Blinkmoth artifact creature with flying and infect until end of turn. It's still a land. (It deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
            new CreatureToken(
                1, 1, "1/1 Phyrexian Blinkmoth artifact creature with flying and infect", SubType.PHYREXIAN, SubType.BLINKMOTH
            ).withType(CardType.ARTIFACT).withAbility(FlyingAbility.getInstance()).withAbility(InfectAbility.getInstance()),
            CardType.LAND,
            Duration.EndOfTurn
            ).setText("{this} becomes a 1/1 Phyrexian Blinkmoth artifact creature with flying and infect until end of turn. "
                + "It's still a land. <i>(It deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)</i>"),
            new GenericManaCost(1)
        ));
    }

    private InkmothNexus(final InkmothNexus card) {
        super(card);
    }

    @Override
    public InkmothNexus copy() {
        return new InkmothNexus(this);
    }
}
