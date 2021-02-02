
package mage.cards.u;

import java.util.UUID;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class UndyingEvil extends CardImpl {

    public UndyingEvil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Target creature gains undying until end of turn.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(new UndyingAbility(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private UndyingEvil(final UndyingEvil card) {
        super(card);
    }

    @Override
    public UndyingEvil copy() {
        return new UndyingEvil(this);
    }
}
