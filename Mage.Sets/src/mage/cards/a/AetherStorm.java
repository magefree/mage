
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class AetherStorm extends CardImpl {

    public AetherStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");

        // Creature spells can't be cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AetherStormReplacementEffect()));

        // Pay 4 life: Destroy Aether Storm. It can't be regenerated. Any player may activate this ability.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroySourceEffect(true), new PayLifeCost(4));
        ability.setMayActivate(TargetController.ANY);
        ability.addEffect(new InfoEffect("Any player may activate this ability"));
        this.addAbility(ability);
    }

    private AetherStorm(final AetherStorm card) {
        super(card);
    }

    @Override
    public AetherStorm copy() {
        return new AetherStorm(this);
    }
}

class AetherStormReplacementEffect extends ContinuousRuleModifyingEffectImpl {

    public AetherStormReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Creature spells can't be cast";
    }

    private AetherStormReplacementEffect(final AetherStormReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AetherStormReplacementEffect copy() {
        return new AetherStormReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getSourceId());
        return card != null && card.isCreature(game);
    }

}
