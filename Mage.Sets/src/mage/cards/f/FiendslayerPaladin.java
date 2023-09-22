
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
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
import mage.game.stack.StackObject;

/**
 *
 * @author jeffwadsworth
 */
public final class FiendslayerPaladin extends CardImpl {

    public FiendslayerPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Fiendslayer Paladin can't be the target of black or red spells your opponents control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new FiendslayerPaladinEffect()));
        
    }

    private FiendslayerPaladin(final FiendslayerPaladin card) {
        super(card);
    }

    @Override
    public FiendslayerPaladin copy() {
        return new FiendslayerPaladin(this);
    }
}

class FiendslayerPaladinEffect extends ContinuousRuleModifyingEffectImpl {

    public FiendslayerPaladinEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "{this} can't be the target of black or red spells your opponents control";
    }

    private FiendslayerPaladinEffect(final FiendslayerPaladinEffect effect) {
        super(effect);
    }

    @Override
    public FiendslayerPaladinEffect copy() {
        return new FiendslayerPaladinEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            return sourcePermanent.getLogName() + " can't be the target of black or red spells opponents control";
        }
        return null;
    }
    
    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card targetCard = game.getCard(event.getTargetId());
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (targetCard != null && stackObject != null && targetCard.getId().equals(source.getSourceId())) {
            if (stackObject.getColor(game).isBlack() || stackObject.getColor(game).isRed()) {
                if (!stackObject.isControlledBy(source.getControllerId())
                        && stackObject.isInstant(game)
                        || stackObject.isSorcery(game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
