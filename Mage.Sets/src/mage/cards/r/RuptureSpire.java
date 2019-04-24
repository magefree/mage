

package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki, North
 */
public final class RuptureSpire extends CardImpl {

    public RuptureSpire (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);

        this.addAbility(new EntersBattlefieldTappedAbility());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl("{1}")), false));
        this.addAbility(new AnyColorManaAbility());
    }

    public RuptureSpire (final RuptureSpire card) {
        super(card);
    }

    @Override
    public RuptureSpire copy() {
        return new RuptureSpire(this);
    }
}