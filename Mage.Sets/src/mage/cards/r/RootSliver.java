package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.CantBeCounteredSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;

/**
 *
 * @author cbt33, BetaSteward (Autumn's Veil, Combust)
 */
public final class RootSliver extends CardImpl {

    public RootSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Root Sliver can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new CantBeCounteredSourceEffect()));
        // Sliver spells can't be countered.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RootSliverEffect()));

    }

    private RootSliver(final RootSliver card) {
        super(card);
    }

    @Override
    public RootSliver copy() {
        return new RootSliver(this);
    }
}

class RootSliverEffect extends ContinuousRuleModifyingEffectImpl {

    public RootSliverEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Sliver spells can't be countered";
    }

    private RootSliverEffect(final RootSliverEffect effect) {
        super(effect);
    }

    @Override
    public RootSliverEffect copy() {
        return new RootSliverEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && spell.hasSubtype(SubType.SLIVER, game)) {
            MageObject sourceObject = game.getObject(event.getSourceId());
            if (sourceObject instanceof StackObject) {
                return true;
            }
        }
        return false;
    }

}
