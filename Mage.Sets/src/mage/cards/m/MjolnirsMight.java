package mage.cards.m;

import java.util.UUID;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPlayer;

/**
 *
 * @author muz
 */
public final class MjolnirsMight extends CardImpl {

    public MjolnirsMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Mjolnir's Might deals 4 damage to target player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetPlayer());

        // Exile the top card of your library. Until the end of your next turn, you may play that card.
        this.getSpellAbility().addEffect(new ExileTopXMayPlayUntilEffect(
            1, Duration.UntilEndOfYourNextTurn
        ).concatBy("<br>"));

    }

    private MjolnirsMight(final MjolnirsMight card) {
        super(card);
    }

    @Override
    public MjolnirsMight copy() {
        return new MjolnirsMight(this);
    }
}
