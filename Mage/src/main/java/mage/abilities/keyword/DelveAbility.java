package mage.abilities.keyword;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ActivationManaAbilityStep;
import mage.abilities.costs.mana.AlternateManaPaymentAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.ManaOptions;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AbilityType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.List;

/**
 * 702.65. Delve
 * <p>
 * 702.65a Delve is a static ability that functions while the spell with delve is on the stack. “Delve” means “For
 * each generic mana in this spell’s total cost, you may exile a card from your graveyard rather than pay that mana.”
 * <p>
 * 702.65b The delve ability isn’t an additional or alternative cost and applies only after the total cost of the spell
 * with delve is determined.
 * <p>
 * 702.65c Multiple instances of delve on the same spell are redundant.
 * <p>
 * The rules for delve have changed slightly since it was last in an expansion. Previously, delve reduced the cost
 * to cast a spell. Under the current rules, you exile cards from your graveyard at the same time you pay the spell’s
 * cost. Exiling a card this way is simply another way to pay that cost. (This is similar to the change made to
 * convoke for the Magic 2015 Core Set.)
 * <p>
 * You can’t exile cards to pay for the colored mana requirements of a spell with delve.
 * <p>
 * You can’t exile more cards than the generic mana requirement of a spell with delve. For example, you can’t exile more
 * than nine cards from your graveyard to cast Dead Drop.
 * <p>
 * Because delve isn’t an alternative cost, it can be used in conjunction with alternative costs.
 *
 * @author LevelX2, JayDi85
 * <p>
 * TODO: Change card exiling to a way to pay mana costs, now it's maybe not
 * possible to pay costs from effects that increase the mana costs.
 * If it real bug then possible fix: choose cards on apply like convoke and improvise, not as cost
 */
public class DelveAbility extends SimpleStaticAbility implements AlternateManaPaymentAbility {

    private static final DynamicValue cardsInGraveyard = new CardsInControllerGraveyardCount();

    public DelveAbility() {
        super(Zone.ALL, null);
        this.setRuleAtTheTop(true);
        this.addHint(new ValueHint("Cards in your graveyard", cardsInGraveyard));
    }

    public DelveAbility(final DelveAbility ability) {
        super(ability);
    }

    @Override
    public DelveAbility copy() {
        return new DelveAbility(this);
    }

    @Override
    public String getRule() {
        return "Delve <i>(Each card you exile from your graveyard while casting this spell pays for {1}.)</i>";
    }

    @Override
    public ActivationManaAbilityStep useOnActivationManaAbilityStep() {
        return ActivationManaAbilityStep.AFTER;
    }

    @Override
    public void addSpecialAction(Ability source, Game game, ManaCost unpaid) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && !controller.getGraveyard().isEmpty()) {
            if (source.getAbilityType() == AbilityType.SPELL && unpaid.getMana().getGeneric() > 0) {
                SpecialAction specialAction = new DelveSpecialAction(this);
                specialAction.setControllerId(source.getControllerId());
                specialAction.setSourceId(source.getSourceId());
                int unpaidAmount = unpaid.getMana().getGeneric();
                if (!controller.getManaPool().isAutoPayment() && unpaidAmount > 1) {
                    unpaidAmount = 1;
                }
                specialAction.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(
                        0, Math.min(controller.getGraveyard().size(), unpaidAmount),
                        new FilterCard("cards from your graveyard"), true)));
                if (specialAction.canActivate(source.getControllerId(), game).canActivate()) {
                    game.getState().getSpecialActions().add(specialAction);
                }
            }
        }
    }

    @Override
    public ManaOptions getManaOptions(Ability source, Game game, ManaCost unpaid) {
        ManaOptions options = new ManaOptions();
        Player controller = game.getPlayer(source.getControllerId());
        int graveCount = cardsInGraveyard.calculate(game, source, null);
        if (controller != null && graveCount > 0) {
            options.addMana(Mana.GenericMana(Math.min(unpaid.getMana().getGeneric(), graveCount)));
        }
        return options;
    }
}

class DelveSpecialAction extends SpecialAction {

    public DelveSpecialAction(AlternateManaPaymentAbility manaAbility) {
        super(Zone.ALL, manaAbility);
        this.abilityType = AbilityType.SPECIAL_MANA_PAYMENT;
        this.addEffect(new DelveEffect());
    }

    public DelveSpecialAction(final DelveSpecialAction ability) {
        super(ability);
    }

    @Override
    public DelveSpecialAction copy() {
        return new DelveSpecialAction(this);
    }
}

class DelveEffect extends OneShotEffect {

    public DelveEffect() {
        super(Outcome.Benefit);
        this.staticText = "Delve (Each card you exile from your graveyard while casting this spell pays for {1}.)";
    }

    public DelveEffect(final DelveEffect effect) {
        super(effect);
    }

    @Override
    public DelveEffect copy() {
        return new DelveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (controller != null && spell != null) {
            ExileFromGraveCost exileFromGraveCost = (ExileFromGraveCost) source.getCosts().get(0);
            List<Card> exiledCards = exileFromGraveCost.getExiledCards();
            if (!exiledCards.isEmpty()) {
                Cards toDelve = new CardsImpl();
                for (Card card : exiledCards) {
                    toDelve.add(card);
                }
                ManaPool manaPool = controller.getManaPool();
                manaPool.addMana(Mana.ColorlessMana(toDelve.size()), game, source);
                manaPool.unlockManaType(ManaType.COLORLESS);
                String keyString = CardUtil.getCardZoneString("delvedCards", source.getSourceId(), game);
                @SuppressWarnings("unchecked")
                Cards delvedCards = (Cards) game.getState().getValue(keyString);
                if (delvedCards == null) {
                    game.getState().setValue(keyString, toDelve);
                } else {
                    delvedCards.addAll(toDelve);
                }

                // can't use mana abilities after that (delve cost must be payed after mana abilities only)
                spell.setCurrentActivatingManaAbilitiesStep(ActivationManaAbilityStep.AFTER);
            }
            return true;
        }
        return false;
    }
}
