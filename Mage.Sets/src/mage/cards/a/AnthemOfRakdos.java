
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.util.CardUtil;

/**
 * @author JotaPeRL
 */
public final class AnthemOfRakdos extends CardImpl {

    public AnthemOfRakdos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{R}{R}");

        // Whenever a creature you control attacks, it gets +2/+0 until end of turn and Anthem of Rakdos deals 1 damage to you.
        Effect effect = new BoostTargetEffect(2, 0, Duration.EndOfTurn);
        effect.setText("it gets +2/+0 until end of turn");
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(effect, false, true);
        effect = new DamageControllerEffect(1);
        effect.setText("and {this} deals 1 damage to you");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Hellbent - As long as you have no cards in hand, if a source you control would deal damage to a creature or player, it deals double that damage to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AnthemOfRakdosHellbentEffect()));
    }

    private AnthemOfRakdos(final AnthemOfRakdos card) {
        super(card);
    }

    @Override
    public AnthemOfRakdos copy() {
        return new AnthemOfRakdos(this);
    }
}

class AnthemOfRakdosHellbentEffect extends ReplacementEffectImpl {

    public AnthemOfRakdosHellbentEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "<i>Hellbent</i> &mdash; As long as you have no cards in hand, if a source you control would deal damage to a permanent or player, it deals double that damage to that permanent or player instead.";
    }

    private AnthemOfRakdosHellbentEffect(final AnthemOfRakdosHellbentEffect effect) {
        super(effect);
    }

    @Override
    public AnthemOfRakdosHellbentEffect copy() {
        return new AnthemOfRakdosHellbentEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getControllerId(event.getSourceId()).equals(source.getControllerId()) && HellbentCondition.instance.apply(game, source);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
