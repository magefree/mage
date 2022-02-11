
package mage.cards.l;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author BetaSteward
 */
public final class LoyalCathar extends CardImpl {

    public LoyalCathar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.secondSideCardClazz = mage.cards.u.UnhallowedCathar.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(VigilanceAbility.getInstance());

        // When Loyal Cathar dies, return it to the battlefield transformed under your control at the beginning of the next end step.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesSourceTriggeredAbility(new LoyalCatharEffect()));
    }

    private LoyalCathar(final LoyalCathar card) {
        super(card);
    }

    @Override
    public LoyalCathar copy() {
        return new LoyalCathar(this);
    }
}

class LoyalCatharEffect extends OneShotEffect {

    private static final String effectText = "return it to the battlefield transformed under your control at the beginning of the next end step";

    LoyalCatharEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    LoyalCatharEffect(LoyalCatharEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        //create delayed triggered ability
        if (Zone.GRAVEYARD == game.getState().getZone(source.getSourceId())) {
            Effect effect = new ReturnLoyalCatharEffect();
            effect.setTargetPointer(new FixedTarget(source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId())));
            game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect), source);
        }
        return true;
    }

    @Override
    public LoyalCatharEffect copy() {
        return new LoyalCatharEffect(this);
    }

}

class ReturnLoyalCatharEffect extends OneShotEffect {

    public ReturnLoyalCatharEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield transformed under your control";
    }

    public ReturnLoyalCatharEffect(final ReturnLoyalCatharEffect effect) {
        super(effect);
    }

    @Override
    public ReturnLoyalCatharEffect copy() {
        return new ReturnLoyalCatharEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null) {
            game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + card.getId(), Boolean.TRUE);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}