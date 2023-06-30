
package mage.cards.b;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class BoundDetermined extends SplitCard {

    public BoundDetermined(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}{G}", "{G}{U}", SpellAbilityType.SPLIT);

        // Bound
        // Sacrifice a creature. Return up to X cards from your graveyard to your hand, where X is the number of colors that creature was. Exile this card.
        getLeftHalfCard().getSpellAbility().addEffect(new BoundEffect());
        Effect effect = new ExileSourceEffect();
        effect.setText("Exile this card");
        getLeftHalfCard().getSpellAbility().addEffect(effect);
        // Determined
        // Other spells you control can't be countered this turn.
        // Draw a card.
        getRightHalfCard().getSpellAbility().addEffect(new DeterminedEffect());
        getRightHalfCard().getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));

    }

    private BoundDetermined(final BoundDetermined card) {
        super(card);
    }

    @Override
    public BoundDetermined copy() {
        return new BoundDetermined(this);
    }
}

class BoundEffect extends OneShotEffect {

    public BoundEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Sacrifice a creature. Return up to X cards from your graveyard to your hand, where X is the number of colors that creature was";
    }

    public BoundEffect(final BoundEffect effect) {
        super(effect);
    }

    @Override
    public BoundEffect copy() {
        return new BoundEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetControlledPermanent target = new TargetControlledPermanent(1, 1, new FilterControlledCreaturePermanent("a creature (to sacrifice)"), true);
            if (target.canChoose(controller.getId(), source, game)) {
                if (controller.chooseTarget(outcome, target, source, game)) {
                    Permanent toSacrifice = game.getPermanent(target.getFirstTarget());
                    if (toSacrifice != null) {
                        toSacrifice.sacrifice(source, game);
                        game.getState().processAction(game);
                        int colors = toSacrifice.getColor(game).getColorCount();
                        if (colors > 0) {
                            TargetCardInYourGraveyard targetCard = new TargetCardInYourGraveyard(0, colors,
                                    new FilterCard("up to " + colors + " card" + (colors > 1 ? "s" : "") + " from your graveyard"));
                            controller.chooseTarget(outcome, targetCard, source, game);
                            controller.moveCards(new CardsImpl(targetCard.getTargets()), Zone.HAND, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class DeterminedEffect extends ContinuousRuleModifyingEffectImpl {

    DeterminedEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Other spells you control can't be countered this turn";
    }

    DeterminedEffect(final DeterminedEffect effect) {
        super(effect);
    }

    @Override
    public DeterminedEffect copy() {
        return new DeterminedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(source);
        if (sourceObject != null) {
            return "This spell can't be countered (" + sourceObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        return spell != null && !spell.getSourceId().equals(source.getSourceId()) && spell.isControlledBy(source.getControllerId());
    }
}
