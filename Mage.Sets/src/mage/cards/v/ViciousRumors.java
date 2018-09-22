package mage.cards.v;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveEachPlayerEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author TheElk801
 */
public final class ViciousRumors extends CardImpl {

    public ViciousRumors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{B}");

        // Vicious Rumors deals 1 damage to each opponent. Each opponent discards a card, then puts the top card of their library into their graveyard. You gain 1 life.
        this.getSpellAbility().addEffect(
                new DamagePlayersEffect(1, TargetController.OPPONENT)
        );
        this.getSpellAbility().addEffect(new DiscardEachPlayerEffect(
                new StaticValue(1), false,
                TargetController.OPPONENT
        ));
        this.getSpellAbility().addEffect(
                new PutTopCardOfLibraryIntoGraveEachPlayerEffect(
                        1, TargetController.OPPONENT
                ).setText(", then puts the top card of their library "
                        + "into their graveyard")
        );
        this.getSpellAbility().addEffect(new GainLifeEffect(1));
    }

    public ViciousRumors(final ViciousRumors card) {
        super(card);
    }

    @Override
    public ViciousRumors copy() {
        return new ViciousRumors(this);
    }
}
