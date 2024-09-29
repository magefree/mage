
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.TrampleAbility;
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
import mage.util.CardUtil;

/**
 *
 * @author LoneFox

 */
public final class EmbermawHellion extends CardImpl {

    public EmbermawHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.HELLION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // If another red source you control would deal damage to a permanent or player, it deals that much damage plus 1 to that permanent or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EmbermawHellionEffect()));
    }

    private EmbermawHellion(final EmbermawHellion card) {
        super(card);
    }

    @Override
    public EmbermawHellion copy() {
        return new EmbermawHellion(this);
    }
}

class EmbermawHellionEffect extends ReplacementEffectImpl {

    EmbermawHellionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If another red source you control would deal damage to a permanent or player, it deals that much damage plus 1 to that permanent or player instead.";
    }

    private EmbermawHellionEffect(final EmbermawHellionEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        switch(event.getType()) {
            case DAMAGE_PERMANENT:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if(source.isControlledBy(game.getControllerId(event.getSourceId()))) {
            MageObject sourceObject;
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if(sourcePermanent == null) {
                sourceObject = game.getObject(event.getSourceId());
            }
            else {
                sourceObject = sourcePermanent;
            }
            return sourceObject != null && sourceObject.getColor(game).isRed() && !sourceObject.getId().equals(source.getSourceId());
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowInc(event.getAmount(), 1));
        return false;
    }

    @Override
    public EmbermawHellionEffect copy() {
        return new EmbermawHellionEffect(this);
    }

}
