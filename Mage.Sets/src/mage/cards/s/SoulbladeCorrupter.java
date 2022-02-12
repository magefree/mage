
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class SoulbladeCorrupter extends CardImpl {

    public SoulbladeCorrupter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Partner with Soulblade Renewer (When this creature enters the battlefield, target player may put Soulblade Renewer into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Soulblade Renewer"));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a creature with a +1/+1 counter on it attacks one of your opponents, that creature gains deathtouch until end of turn.
        this.addAbility(new SoulbladeCorrupterTriggeredAbility());
    }

    private SoulbladeCorrupter(final SoulbladeCorrupter card) {
        super(card);
    }

    @Override
    public SoulbladeCorrupter copy() {
        return new SoulbladeCorrupter(this);
    }
}

class SoulbladeCorrupterTriggeredAbility extends AttacksAllTriggeredAbility {

    SoulbladeCorrupterTriggeredAbility() {
        super(new GainAbilityTargetEffect(
                DeathtouchAbility.getInstance(),
                Duration.EndOfTurn
        ).setText("that creature gains deathtouch until end of turn"), false, StaticFilters.FILTER_CREATURE_P1P1, SetTargetPointer.PERMANENT, false);
    }

    SoulbladeCorrupterTriggeredAbility(final SoulbladeCorrupterTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                Player player = game.getPlayer(permanent.getControllerId());
                if (player != null && player.hasOpponent(getControllerId(), game)) {
                    getEffects().setTargetPointer(new FixedTarget(permanent, game));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature with a +1/+1 counter on it attacks one of your opponents, that creature gains deathtouch until end of turn.";
    }

    @Override
    public SoulbladeCorrupterTriggeredAbility copy() {
        return new SoulbladeCorrupterTriggeredAbility(this);
    }

}
