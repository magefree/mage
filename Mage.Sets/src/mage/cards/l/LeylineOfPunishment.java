

package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.keyword.LeylineAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class LeylineOfPunishment extends CardImpl {

    public LeylineOfPunishment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}{R}");

        // If Leyline of Punishment is in your opening hand, you may begin the game with it on the battlefield.
        this.addAbility(LeylineAbility.getInstance());
        // Players can't gain life.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantGainLifeAllEffect()));
        // Damage can't be prevented.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LeylineOfPunishmentEffect2()));
    }

    private LeylineOfPunishment(final LeylineOfPunishment card) {
        super(card);
    }

    @Override
    public LeylineOfPunishment copy() {
        return new LeylineOfPunishment(this);
    }

}

class LeylineOfPunishmentEffect2 extends ContinuousRuleModifyingEffectImpl {

    public LeylineOfPunishmentEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Damage can't be prevented";
    }

    private LeylineOfPunishmentEffect2(final LeylineOfPunishmentEffect2 effect) {
        super(effect);
    }

    @Override
    public LeylineOfPunishmentEffect2 copy() {
        return new LeylineOfPunishmentEffect2(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PREVENT_DAMAGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }

}
