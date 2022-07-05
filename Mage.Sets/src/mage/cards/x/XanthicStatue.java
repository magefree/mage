
package mage.cards.x;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author LoneFox
 */
public final class XanthicStatue extends CardImpl {

    public XanthicStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{8}");

        // {5}: Until end of turn, Xanthic Statue becomes an 8/8 Golem artifact creature with trample.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new XanthicStatueCreature(),
            "", Duration.EndOfTurn), new ManaCostsImpl<>("{5}")));
    }

    private XanthicStatue(final XanthicStatue card) {
        super(card);
    }

    @Override
    public XanthicStatue copy() {
        return new XanthicStatue(this);
    }
}

class XanthicStatueCreature extends TokenImpl {

    public XanthicStatueCreature() {
        super("Xanthic Statue", "8/8 Golem artifact creature with trample");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        power = new MageInt(8);
        toughness = new MageInt(8);

        this.addAbility(TrampleAbility.getInstance());
    }
    public XanthicStatueCreature(final XanthicStatueCreature token) {
        super(token);
    }

    public XanthicStatueCreature copy() {
        return new XanthicStatueCreature(this);
    }
}
