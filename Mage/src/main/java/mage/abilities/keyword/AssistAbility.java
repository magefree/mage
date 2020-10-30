package mage.abilities.keyword;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ActivationManaAbilityStep;
import mage.abilities.costs.mana.AlternateManaPaymentAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ManaOptions;
import mage.constants.*;
import mage.filter.FilterPlayer;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPlayer;
import mage.util.ManaUtil;

/**
 * 702.131. Assist
 * <p>
 * 702.131a Assist is a static ability that modifies the rules of paying for the
 * spell with assist (see rules 601.2g-h). If the total cost to cast a spell
 * with assist includes a generic mana component, before you activate mana
 * abilities while casting it, you may choose another player. That player has a
 * chance to activate mana abilities. Once that player chooses not to activate
 * any more mana abilities, you have a chance to activate mana abilities. Before
 * you begin to pay the total cost of the spell, the player you chose may pay
 * for any amount of the generic mana in the spell’s total cost.
 *
 * @author emerald000, JayDi85
 */
public class AssistAbility extends SimpleStaticAbility implements AlternateManaPaymentAbility {

    private static final FilterPlayer filter = new FilterPlayer("another player");

    static {
        filter.add(TargetController.NOT_YOU.getPlayerPredicate());
    }

    public AssistAbility() {
        super(Zone.ALL, null);
        this.setRuleAtTheTop(true);
    }

    public AssistAbility(final AssistAbility ability) {
        super(ability);
    }

    @Override
    public AssistAbility copy() {
        return new AssistAbility(this);
    }

    @Override
    public String getRule() {
        return "Assist <i>(Another player can help pay the generic mana of this spell's cost.)</i>";
    }

    @Override
    public ActivationManaAbilityStep useOnActivationManaAbilityStep() {
        return ActivationManaAbilityStep.BEFORE;
    }

    @Override
    public void addSpecialAction(Ability source, Game game, ManaCost unpaid) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null
                && source.getAbilityType() == AbilityType.SPELL
                && unpaid.getMana().getGeneric() >= 1
                && game.getState().getValue(source.getSourceId().toString() + game.getState().getZoneChangeCounter(source.getSourceId()) + "_assisted") == null) {
            SpecialAction specialAction = new AssistSpecialAction(unpaid, this);
            specialAction.setControllerId(source.getControllerId());
            specialAction.setSourceId(source.getSourceId());
            Target target = new TargetPlayer(1, 1, true, filter);
            specialAction.addTarget(target);
            if (specialAction.canActivate(source.getControllerId(), game).canActivate()) {
                game.getState().getSpecialActions().add(specialAction);
            }
        }
    }

    @Override
    public ManaOptions getManaOptions(Ability source, Game game, ManaCost unpaid) {
        ManaOptions options = new ManaOptions();
        if (unpaid.getMana().getGeneric() == 0) {
            // nothing to pay
            return options;
        }

        // AI can't use assist (can't ask another player to help), maybe in teammode it can be enabled, but tests must works all the time
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !controller.isTestMode() && !controller.isHuman()) {
            return options;
        }

        // search opponents who can help with generic pay
        int opponentCanPayMax = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent != null) {
                // basic and pool, but no coditional mana
                ManaOptions availableMana = opponent.getManaAvailable(game);
//                availableMana.addMana(opponent.getManaPool().getMana());
                for (Mana mana : availableMana) {
                    if (mana.count() > 0) {
                        opponentCanPayMax = Math.max(opponentCanPayMax, mana.count());
                    }
                }
            }
        }

        if (opponentCanPayMax > 0) {
            options.addMana(Mana.GenericMana(Math.min(unpaid.getMana().getGeneric(), opponentCanPayMax)));
        }

        return options;
    }
}

class AssistSpecialAction extends SpecialAction {

    AssistSpecialAction(ManaCost unpaid, AlternateManaPaymentAbility manaAbility) {
        super(Zone.ALL, manaAbility);
        this.abilityType = AbilityType.SPECIAL_MANA_PAYMENT;
        setRuleVisible(false);
        this.addEffect(new AssistEffect(unpaid));
    }

    AssistSpecialAction(final AssistSpecialAction ability) {
        super(ability);
    }

    @Override
    public AssistSpecialAction copy() {
        return new AssistSpecialAction(this);
    }
}

class AssistEffect extends OneShotEffect {

    private final ManaCost unpaid;

    AssistEffect(ManaCost unpaid) {
        super(Outcome.Benefit);
        this.unpaid = unpaid;
        this.staticText = "Assist <i>(Another player can help pay the generic mana of this spell's cost.)</i>";
    }

    AssistEffect(final AssistEffect effect) {
        super(effect);
        this.unpaid = effect.unpaid;
    }

    @Override
    public AssistEffect copy() {
        return new AssistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (controller != null && spell != null && targetPlayer != null) {
            // AI can't assist other players, maybe for teammates only (but tests must work as normal)
            int amountToPay = 0;
            if (targetPlayer.isHuman() || targetPlayer.isTestMode()) {
                amountToPay = targetPlayer.announceXMana(0, unpaid.getMana().getGeneric(),
                        "How much mana to pay as assist for " + controller.getName() + "?", game, source);
            }

            if (amountToPay > 0) {
                Cost cost = ManaUtil.createManaCost(amountToPay, false);
                if (cost.pay(source, game, source.getSourceId(), targetPlayer.getId(), false)) {
                    ManaPool manaPool = controller.getManaPool();
                    manaPool.addMana(Mana.ColorlessMana(amountToPay), game, source);
                    manaPool.unlockManaType(ManaType.COLORLESS); // it's unlock mana for one use/click, but it can gives more
                    game.informPlayers(targetPlayer.getLogName() + " paid {" + amountToPay + "} for " + controller.getLogName());
                    game.getState().setValue(source.getSourceId().toString() + game.getState().getZoneChangeCounter(source.getSourceId()) + "_assisted", true);
                }

                // assist must be used before activating mana abilities, so no need to switch step after usage
                // (mana and other special abilities can be used after assist)
                //spell.setCurrentActivatingManaAbilitiesStep(ActivationManaAbilityStep.NORMAL);
            }
            return true;
        }
        return false;
    }
}
