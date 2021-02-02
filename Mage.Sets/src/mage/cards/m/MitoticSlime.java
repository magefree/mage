
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Ooze2Token;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class MitoticSlime extends CardImpl {

    public MitoticSlime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.OOZE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new Ooze2Token(), 2), false));
    }

    private MitoticSlime(final MitoticSlime card) {
        super(card);
    }

    @Override
    public MitoticSlime copy() {
        return new MitoticSlime(this);
    }

}
