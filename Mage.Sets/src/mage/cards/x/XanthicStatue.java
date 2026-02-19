
package mage.cards.x;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author LoneFox
 */
public final class XanthicStatue extends CardImpl {

    public XanthicStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{8}");

        // {5}: Until end of turn, Xanthic Statue becomes an 8/8 Golem artifact creature with trample.
        this.addAbility(new SimpleActivatedAbility(
            new BecomesCreatureSourceEffect(
                new CreatureToken(8, 8, "8/8 Golem artifact creature with trample", SubType.GOLEM)
                    .withAbility(TrampleAbility.getInstance()),
                CardType.ARTIFACT,
                Duration.EndOfTurn
            ).setText("until end of turn, {this} becomes an 8/8 Golem artifact creature with trample"),
            new ManaCostsImpl<>("{5}")
        ));
    }

    private XanthicStatue(final XanthicStatue card) {
        super(card);
    }

    @Override
    public XanthicStatue copy() {
        return new XanthicStatue(this);
    }
}
