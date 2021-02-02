
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ThopterColorlessToken;

/**
 *
 * @author Styxo
 */
public final class FiligreeCrawler extends CardImpl {

    public FiligreeCrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Filigree Crawler dies, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new ThopterColorlessToken())));
    }

    private FiligreeCrawler(final FiligreeCrawler card) {
        super(card);
    }

    @Override
    public FiligreeCrawler copy() {
        return new FiligreeCrawler(this);
    }
}
