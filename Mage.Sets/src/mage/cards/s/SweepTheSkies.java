package mage.cards.s;

import mage.abilities.dynamicvalue.common.ColorsOfManaSpentToCastCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SweepTheSkies extends CardImpl {

    public SweepTheSkies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Converge — Create a 1/1 colorless Thopter artifact creature token with flying for each color of mana spent to cast this spell.
        this.getSpellAbility().addEffect(new CreateTokenEffect(
                new ThopterColorlessToken(), ColorsOfManaSpentToCastCount.getInstance()
        ).setText("<i>Converge</i> &mdash; Create a 1/1 colorless Thopter artifact creature token with flying for each color of mana spent to cast this spell"));
    }

    private SweepTheSkies(final SweepTheSkies card) {
        super(card);
    }

    @Override
    public SweepTheSkies copy() {
        return new SweepTheSkies(this);
    }
}
