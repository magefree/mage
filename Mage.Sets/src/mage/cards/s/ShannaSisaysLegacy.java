
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;

/**
 *
 * @author TheElk801
 */
public final class ShannaSisaysLegacy extends CardImpl {

    public ShannaSisaysLegacy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Shanna, Sisay's Legacy can't be the target of abilities your opponents control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ShannaSisaysLegacyEffect()));

        // Shanna gets +1/+1 for each creature you control.
        DynamicValue value = new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_CREATURES);
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(value, value, Duration.WhileOnBattlefield)
                        .setText("{this} gets +1/+1 for each creature you control")
        ));
    }

    private ShannaSisaysLegacy(final ShannaSisaysLegacy card) {
        super(card);
    }

    @Override
    public ShannaSisaysLegacy copy() {
        return new ShannaSisaysLegacy(this);
    }
}

class ShannaSisaysLegacyEffect extends ContinuousRuleModifyingEffectImpl {

    public ShannaSisaysLegacyEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "{this} can't be the target of abilities your opponents control";
    }

    public ShannaSisaysLegacyEffect(final ShannaSisaysLegacyEffect effect) {
        super(effect);
    }

    @Override
    public ShannaSisaysLegacyEffect copy() {
        return new ShannaSisaysLegacyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            return sourcePermanent.getLogName() + " can't be the target of abilities you control";
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
            if (stackObject instanceof Ability) {
                if (!stackObject.isControlledBy(source.getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }
}
