
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 *
 * @author emerald000
 */
public final class KarplusanStrider extends CardImpl {

    public KarplusanStrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.YETI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Karplusan Strider can't be the target of blue or black spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KarplusanStriderEffect()));
    }

    private KarplusanStrider(final KarplusanStrider card) {
        super(card);
    }

    @Override
    public KarplusanStrider copy() {
        return new KarplusanStrider(this);
    }
}

class KarplusanStriderEffect extends ContinuousRuleModifyingEffectImpl {

    KarplusanStriderEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "{this} can't be the target of blue or black spells";
    }

    private KarplusanStriderEffect(final KarplusanStriderEffect effect) {
        super(effect);
    }

    @Override
    public KarplusanStriderEffect copy() {
        return new KarplusanStriderEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            return sourcePermanent.getLogName() + " can't be the target of blue or black spells";
        }
        return null;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(source.getSourceId())) {
            Permanent targettedPermanent = game.getPermanent(event.getTargetId());
            Spell sourceSpell = game.getStack().getSpell(event.getSourceId());
            if (targettedPermanent != null && sourceSpell != null) {
                return sourceSpell.getColor(game).isBlue() || sourceSpell.getColor(game).isBlack();
            }
        }
        return false;
    }
}
