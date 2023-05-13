
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 *
 * @author JRHerlehy
 */
public final class BartelRuneaxe extends CardImpl {

    public BartelRuneaxe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT, SubType.WARRIOR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Bartel Runeaxe can't be the target of Aura spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BartelRuneaxeEffect()));
    }

    private BartelRuneaxe(final BartelRuneaxe card) {
        super(card);
    }

    @Override
    public BartelRuneaxe copy() {
        return new BartelRuneaxe(this);
    }
}

class BartelRuneaxeEffect extends ContinuousRuleModifyingEffectImpl {

    public BartelRuneaxeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "{this} can't be the target of Aura spells";
    }

    public BartelRuneaxeEffect(final BartelRuneaxeEffect effect) {
        super(effect);
    }

    @Override
    public BartelRuneaxeEffect copy() {
        return new BartelRuneaxeEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            return sourcePermanent.getLogName() + " can't be the target of Aura spells";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
            StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
            if (stackObject != null && event.getTargetId().equals(source.getSourceId())) {
                if (stackObject.hasSubtype(SubType.AURA, game)) {
                    return true;
                }
            }
        return false;
    }
}
