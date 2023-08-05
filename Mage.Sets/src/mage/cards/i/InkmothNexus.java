

package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.InfectAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author Loki
 */
public final class InkmothNexus extends CardImpl {

    public InkmothNexus (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        
        // {1}: Inkmoth Nexus becomes a 1/1 Blinkmoth artifact creature with flying and infect until end of turn. It's still a land. (It deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)
        Effect effect = new BecomesCreatureSourceEffect(new InkmothNexusToken(), CardType.LAND, Duration.EndOfTurn);
        effect.setText("{this} becomes a 1/1 Phyrexian Blinkmoth artifact creature with flying and infect until end of turn. It's still a land. <i>(It deals damage to creatures in the form of -1/-1 counters and to players in the form of poison counters.)</i>");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(1)));
    }

    public InkmothNexus (final InkmothNexus card) {
        super(card);
    }

    @Override
    public InkmothNexus copy() {
        return new InkmothNexus(this);
    }
}

class InkmothNexusToken extends TokenImpl {
    public InkmothNexusToken() {
        super("Phyrexian Blinkmoth", "1/1 Phyrexian Blinkmoth artifact creature with flying and infect");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.BLINKMOTH);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(InfectAbility.getInstance());
    }
    public InkmothNexusToken(final InkmothNexusToken token) {
        super(token);
    }

    public InkmothNexusToken copy() {
        return new InkmothNexusToken(this);
    }
}
