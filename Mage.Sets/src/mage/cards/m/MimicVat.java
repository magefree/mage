
package mage.cards.m;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author nantuko
 */
public final class MimicVat extends CardImpl {

    public MimicVat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Imprint - Whenever a nontoken creature dies, you may exile that card. If you do, return each other card exiled with Mimic Vat to its owner's graveyard.
        this.addAbility(new MimicVatTriggeredAbility());

        // {3}, {tap}: Create a token that's a copy of the exiled card. It gains haste. Exile it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MimicVatCreateTokenEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private MimicVat(final MimicVat card) {
        super(card);
    }

    @Override
    public MimicVat copy() {
        return new MimicVat(this);
    }
}

class MimicVatTriggeredAbility extends TriggeredAbilityImpl {

    MimicVatTriggeredAbility() {
        super(Zone.BATTLEFIELD, new MimicVatEffect(), true);
    }

    MimicVatTriggeredAbility(MimicVatTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MimicVatTriggeredAbility copy() {
        return new MimicVatTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // make sure card is on battlefield
        UUID sourceCardId = getSourceId();
        if (game.getPermanent(sourceCardId) == null) {
            // or it is being removed
            if (game.getLastKnownInformation(sourceCardId, Zone.BATTLEFIELD) == null) {
                return false;
            }
        }

        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();

        if (permanent != null
                && zEvent.isDiesEvent()
                && !(permanent instanceof PermanentToken)
                && permanent.isCreature(game)) {

            getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId(), game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return AbilityWord.IMPRINT.formatWord() + "Whenever a nontoken creature dies, you may exile that card. If you do, return each other card exiled with {this} to its owner's graveyard.";
    }
}

class MimicVatEffect extends OneShotEffect {

    public MimicVatEffect() {
        super(Outcome.Benefit);
        staticText = "exile that card";
    }

    public MimicVatEffect(MimicVatEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || permanent == null) {
            return false;
        }
        // Imprint a new one
        Card newCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (newCard != null) {
            // return older cards to graveyard
            Set<Card> toGraveyard = new HashSet<>();
            for (UUID imprintedId : permanent.getImprinted()) {
                Card card = game.getCard(imprintedId);
                if (card != null) {
                    toGraveyard.add(card);
                }
            }
            controller.moveCards(toGraveyard, Zone.GRAVEYARD, source, game);
            permanent.clearImprinted(game);

            controller.moveCardsToExile(newCard, source, game, true, source.getSourceId(), permanent.getName() + " (Imprint)");
            permanent.imprint(newCard.getId(), game);
        }

        return true;
    }

    @Override
    public MimicVatEffect copy() {
        return new MimicVatEffect(this);
    }

}

class MimicVatCreateTokenEffect extends OneShotEffect {

    public MimicVatCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a token that's a copy of a card exiled with {this}. It gains haste. Exile it at the beginning of the next end step";
    }

    public MimicVatCreateTokenEffect(final MimicVatCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public MimicVatCreateTokenEffect copy() {
        return new MimicVatCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }

        if (!permanent.getImprinted().isEmpty()) {
            Card card = game.getCard(permanent.getImprinted().get(0));
            if (card != null) {
                CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(source.getControllerId(), null, true);
                effect.setTargetPointer(new FixedTarget(card, game));
                effect.apply(game, source);
                for (Permanent addedToken : effect.getAddedPermanents()) {
                    ExileTargetEffect exileEffect = new ExileTargetEffect();
                    exileEffect.setTargetPointer(new FixedTarget(addedToken, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }

                return true;
            }
        }

        return false;
    }

}
