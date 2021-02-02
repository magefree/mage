
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class ItOfTheHorridSwarm extends CardImpl {

    public ItOfTheHorridSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Emerge {6}{G}
        this.addAbility(new EmergeAbility(this, new ManaCostsImpl<>("{6}{G}")));
        
        // When you cast It of the Horrid Swarm, create two 1/1 green Insect creature tokens.
        this.addAbility(new CastSourceTriggeredAbility(new CreateTokenEffect(new InsectToken(), 2)));
    }

    private ItOfTheHorridSwarm(final ItOfTheHorridSwarm card) {
        super(card);
    }

    @Override
    public ItOfTheHorridSwarm copy() {
        return new ItOfTheHorridSwarm(this);
    }
}
