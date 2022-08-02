
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public final class TianaShipsCaretaker extends CardImpl {

    public TianaShipsCaretaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever an Aura or Equipment you control is put into a graveyard from the battlefield, you may return that card to its owner's hand at the beginning of the next end step.
        this.addAbility(new TianaShipsCaretakerTriggeredAbility());
    }

    private TianaShipsCaretaker(final TianaShipsCaretaker card) {
        super(card);
    }

    @Override
    public TianaShipsCaretaker copy() {
        return new TianaShipsCaretaker(this);
    }
}

class TianaShipsCaretakerTriggeredAbility extends TriggeredAbilityImpl {

    TianaShipsCaretakerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TianaShipsCaretakerEffect(), true);
        setTriggerPhrase("Whenever an Aura or Equipment you control is put into a graveyard from the battlefield, ");
    }

    TianaShipsCaretakerTriggeredAbility(final TianaShipsCaretakerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TianaShipsCaretakerTriggeredAbility copy() {
        return new TianaShipsCaretakerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getTarget() == null) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(zEvent.getTarget().getId());

        if (permanent != null && zEvent.isDiesEvent()
                && (permanent.isArtifact(game) && permanent.hasSubtype(SubType.EQUIPMENT, game)
                    || permanent.isEnchantment(game) && permanent.hasSubtype(SubType.AURA, game))
                && permanent.isControlledBy(this.controllerId)) {
            this.getEffects().setTargetPointer(new FixedTarget(zEvent.getTargetId()));
            return true;
        }
        return false;
    }
}

class TianaShipsCaretakerEffect extends OneShotEffect {

    TianaShipsCaretakerEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may return that card to its owner's hand at the beginning of the next end step";
    }

    TianaShipsCaretakerEffect(final TianaShipsCaretakerEffect effect) {
        super(effect);
    }

    @Override
    public TianaShipsCaretakerEffect copy() {
        return new TianaShipsCaretakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            Effect effect = new ReturnFromGraveyardToHandTargetEffect();
            effect.setTargetPointer(new FixedTarget(card, game));
            effect.setText("return that card to your hand at the beginning of the next end step");
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
            return true;
        }
        return false;
    }
}
