
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.target.TargetPlayer;
import mage.watchers.common.AmountOfDamageAPlayerReceivedThisTurnWatcher;

/**
 *
 * @author LoneFox
 */
public final class FinalPunishment extends CardImpl {

    public FinalPunishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Target player loses life equal to the damage already dealt to that player this turn.
        Effect effect = new LoseLifeTargetEffect(new FinalPunishmentAmount());
        effect.setText("target player loses life equal to the damage already dealt to that player this turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addWatcher(new AmountOfDamageAPlayerReceivedThisTurnWatcher());
    }

    private FinalPunishment(final FinalPunishment card) {
        super(card);
    }

    @Override
    public FinalPunishment copy() {
        return new FinalPunishment(this);
    }
}

class FinalPunishmentAmount implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        AmountOfDamageAPlayerReceivedThisTurnWatcher watcher
            = game.getState().getWatcher(AmountOfDamageAPlayerReceivedThisTurnWatcher.class);
        if(watcher != null) {
            return watcher.getAmountOfDamageReceivedThisTurn(sourceAbility.getFirstTarget());
        }
        return 0;
    }

    @Override
    public FinalPunishmentAmount copy() {
        return new FinalPunishmentAmount();
    }

    @Override
    public String getMessage() {
        return "the damage already dealt to that player this turn";
    }
}
