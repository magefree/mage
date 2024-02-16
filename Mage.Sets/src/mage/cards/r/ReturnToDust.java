package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.game.stack.Spell;

/**
 * @author emerald000
 */
public final class ReturnToDust extends CardImpl {

    public ReturnToDust(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Exile target artifact or enchantment
        this.getSpellAbility().addEffect(new ReturnToDustExileEffect());
        // If you cast this spell during your main phase, you may exile up to one other target artifact or enchantment
        ConditionalOneShotEffect returnToDustConditionalExileEffect
                = new ConditionalOneShotEffect(new ReturnToDustConditionalExileEffect(), ReturnToDustCondition.instance);
        returnToDustConditionalExileEffect.setText("If you cast this spell during your main phase, you may exile up to one other target artifact or enchantment");
        this.getSpellAbility().addEffect(returnToDustConditionalExileEffect);
        this.getSpellAbility().addTarget(new TargetPermanent(1, 2, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));

        // Two effects are needed to handle cards like Ranar the Ever-Watchful.  Rule 608.2e
    }

    private ReturnToDust(final ReturnToDust card) {
        super(card);
    }

    @Override
    public ReturnToDust copy() {
        return new ReturnToDust(this);
    }
}

class ReturnToDustExileEffect extends OneShotEffect {

    ReturnToDustExileEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target artifact or enchantment";
    }

    private ReturnToDustExileEffect(final ReturnToDustExileEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToDustExileEffect copy() {
        return new ReturnToDustExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // Exile the first target
        Permanent firstTarget = game.getPermanent(source.getFirstTarget());
        if (firstTarget != null) {
            controller.moveCards(firstTarget, Zone.EXILED, source, game);
            return true;
        }
        return false;
    }
}

class ReturnToDustConditionalExileEffect extends OneShotEffect {

    ReturnToDustConditionalExileEffect() {
        super(Outcome.Detriment);
    }

    private ReturnToDustConditionalExileEffect(final ReturnToDustConditionalExileEffect effect) {
        super(effect);
    }

    @Override
    public ReturnToDustConditionalExileEffect copy() {
        return new ReturnToDustConditionalExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (source.getTargets().get(0).getSize() > 1) {
            Permanent secondTarget = game.getPermanent(source.getTargets().get(0).getTargets().get(1));
            if (secondTarget != null
                    && controller.chooseUse(Outcome.Detriment, "Exile the second permanent?", source, game)) {
                controller.moveCards(secondTarget, Zone.EXILED, source, game);
                return true;
            }
        }
        return false;
    }
}

enum ReturnToDustCondition implements Condition {

    /*If a spell or ability copies Return to Dust, the copy exiles 
    only the first target artifact or enchantment. 
    This is because the copy wasn't cast at all. (2021-03-19)
     */
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(source.getSourceId());
        return (game.isActivePlayer(source.getControllerId())
                && game.getTurnPhaseType().isMain())
                && spell != null
                && !spell.isCopy();
    }
}
