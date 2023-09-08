
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author jeffwadsworth
 */
public final class SteelGolem extends CardImpl {

    public SteelGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{3}");
        this.subtype.add(SubType.GOLEM);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // You can't cast creature spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SteelGolemEffect()));
    }

    private SteelGolem(final SteelGolem card) {
        super(card);
    }

    @Override
    public SteelGolem copy() {
        return new SteelGolem(this);
    }
}

class SteelGolemEffect extends ContinuousRuleModifyingEffectImpl {

    public SteelGolemEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You can't cast creature spells";
    }

    private SteelGolemEffect(final SteelGolemEffect effect) {
        super(effect);
    }

    @Override
    public SteelGolemEffect copy() {
        return new SteelGolemEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Card card = game.getCard(event.getSourceId());
            return card != null && card.isCreature(game);
        }
        return false;
    }

}
