package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class StasisSnare extends CardImpl {

    public StasisSnare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}{W}");

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Stasis Snare enters the battlefield, exile target creature an opponent controls until Stasis Snare leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private StasisSnare(final StasisSnare card) {
        super(card);
    }

    @Override
    public StasisSnare copy() {
        return new StasisSnare(this);
    }
}
