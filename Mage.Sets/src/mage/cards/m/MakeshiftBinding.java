package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MakeshiftBinding extends CardImpl {

    public MakeshiftBinding(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // When Makeshift Binding enters the battlefield, exile target creature an opponent controls until Makeshift Binding leaves the battlefield. You gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addEffect(new GainLifeEffect(2));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private MakeshiftBinding(final MakeshiftBinding card) {
        super(card);
    }

    @Override
    public MakeshiftBinding copy() {
        return new MakeshiftBinding(this);
    }
}
