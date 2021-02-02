

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SteppeLynx extends CardImpl {

    public SteppeLynx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.CAT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        this.addAbility(new LandfallAbility(new BoostSourceEffect(2, 2, Duration.EndOfTurn), false));
    }

    private SteppeLynx(final SteppeLynx card) {
        super(card);
    }

    @Override
    public SteppeLynx copy() {
        return new SteppeLynx(this);
    }

}
