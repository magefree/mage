

package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainProtectionFromColorTargetEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SejiriSteppe extends CardImpl {

    public SejiriSteppe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},null);
        this.addAbility(new EntersBattlefieldTappedAbility());
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
        this.addAbility(new WhiteManaAbility());
    }

    private SejiriSteppe(final SejiriSteppe card) {
        super(card);
    }

    @Override
    public SejiriSteppe copy() {
        return new SejiriSteppe(this);
    }

}
