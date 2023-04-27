package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.CastOnlyDuringPhaseStepSourceAbility;
import mage.abilities.condition.common.AfterBlockersAreDeclaredCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TurnPhase;
import mage.filter.common.FilterCreatureAttackingYou;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SaprolingToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author noahg
 */
public final class FlashFoliage extends CardImpl {

    public FlashFoliage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");


        // Cast Flash Foliage only during combat after blockers are declared.
        this.addAbility(new CastOnlyDuringPhaseStepSourceAbility(TurnPhase.COMBAT, AfterBlockersAreDeclaredCondition.instance));

        // Create a 1/1 green Saproling creature token that’s blocking target creature attacking you.
        this.getSpellAbility().addEffect(new FlashFoliageEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreatureAttackingYou()));

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private FlashFoliage(final FlashFoliage card) {
        super(card);
    }

    @Override
    public FlashFoliage copy() {
        return new FlashFoliage(this);
    }
}

class FlashFoliageEffect extends OneShotEffect {

    public FlashFoliageEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a 1/1 green Saproling creature token that's blocking target creature attacking you";
    }

    public FlashFoliageEffect(final FlashFoliageEffect effect) {
        super(effect);
    }

    @Override
    public FlashFoliageEffect copy() {
        return new FlashFoliageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller != null) {
            Token token = new SaprolingToken();
            token.putOntoBattlefield(1, game, source, source.getControllerId());
            Permanent attackingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (attackingCreature != null && game.getState().getCombat() != null) {
                // Possible ruling (see Aetherplasm)
                // The token you created is blocking the attacking creature,
                // even if the block couldn't legally be declared (for example, if that creature
                // enters the battlefield tapped, or it can't block, or the attacking creature
                // has protection from it)
                CombatGroup combatGroup = game.getState().getCombat().findGroup(attackingCreature.getId());
                if (combatGroup != null) {
                    for (UUID tokenId : token.getLastAddedTokenIds()) {
                        Permanent saprolingToken = game.getPermanent(tokenId);
                        if (saprolingToken != null) {
                            combatGroup.addBlocker(tokenId, source.getControllerId(), game);
                            game.getCombat().addBlockingGroup(tokenId, attackingCreature.getId(), controller.getId(), game);
                        }
                    }
                    combatGroup.pickBlockerOrder(attackingCreature.getControllerId(), game);
                }
            }
            return true;
        }
        return false;
    }
}
