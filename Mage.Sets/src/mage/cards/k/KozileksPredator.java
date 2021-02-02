

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EldraziSpawnToken;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class KozileksPredator extends CardImpl {

    public KozileksPredator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new EldraziSpawnToken(), 2), false));
    }

    private KozileksPredator(final KozileksPredator card) {
        super(card);
    }

    @Override
    public KozileksPredator copy() {
        return new KozileksPredator(this);
    }

}
