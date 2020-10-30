package mage.abilities.keyword;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ActivationManaAbilityStep;
import mage.abilities.costs.mana.AlternateManaPaymentAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.ManaOptions;
import mage.choices.Choice;
import mage.choices.ChoiceColor;
import mage.constants.AbilityType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.ManaPool;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * 502.46. Convoke
 * <p>
 * 502.46a Convoke is a static ability that functions while the spell is on the stack. "Convoke"
 * means "As an additional cost to play this spell, you may tap any number of untapped creatures
 * you control. Each creature tapped this way reduces the cost to play this spell by {1} or by
 * one mana of any of that creature's colors." Using the convoke ability follows the rules for
 * paying additional costs in rules 409.1b and 4091f-h.
 * <p>
 * Example: You play Guardian of Vitu-Ghazi, a spell with convoke that costs {3}{G}{W}. You announce
 * that you're going to tap an artifact creature, a red creature, and a green-and-white creature to
 * help pay for it. The artifact creature and the red creature each reduce the spell's cost by {1}.
 * You choose whether the green-white creature reduces the spell's cost by {1}, {G}, or {W}. Then
 * the creatures become tapped as you pay Guardian of Vitu-Ghazi's cost.
 * <p>
 * 502.46b Convoke can't reduce the cost to play a spell to less than 0.
 * <p>
 * 502.46c Multiple instances of convoke on the same spell are redundant.
 * <p>
 * You can tap only untapped creatures you control to reduce the cost of a spell with convoke
 * that you play.
 * <p>
 * While playing a spell with convoke, if you control a creature that taps to produce mana, you
 * can either tap it for mana or tap it to reduce the cost of the spell, but not both.
 * <p>
 * If you tap a multicolored creature to reduce the cost of a spell with convoke, you reduce
 * the cost by {1} or by one mana of your choice of any of that creature's colors.
 * <p>
 * Convoke doesn't change a spell's mana cost or converted mana cost.
 *
 * @author LevelX2, JayDi85
 */
public class ConvokeAbility extends SimpleStaticAbility implements AlternateManaPaymentAbility {

    private static final FilterControlledCreaturePermanent filterUntapped = new FilterControlledCreaturePermanent();

    static {
        filterUntapped.add(Predicates.not(TappedPredicate.instance));
    }

    public ConvokeAbility() {
        super(Zone.ALL, null); // all AlternateManaPaymentAbility must use ALL zone to calculate playable abilities
        this.setRuleAtTheTop(true);
        this.addHint(new ValueHint("Untapped creatures you control", new PermanentsOnBattlefieldCount(filterUntapped)));
    }

    public ConvokeAbility(final ConvokeAbility ability) {
        super(ability);
    }

    @Override
    public ConvokeAbility copy() {
        return new ConvokeAbility(this);
    }

    @Override
    public String getRule() {
        return "Convoke <i>(Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color.)</i>";
    }

    @Override
    public ActivationManaAbilityStep useOnActivationManaAbilityStep() {
        return ActivationManaAbilityStep.AFTER;
    }

    @Override
    public void addSpecialAction(Ability source, Game game, ManaCost unpaid) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && game.getBattlefield().contains(filterUntapped, controller.getId(), 1, game)) {
            if (source.getAbilityType() == AbilityType.SPELL) {
                SpecialAction specialAction = new ConvokeSpecialAction(unpaid, this);
                specialAction.setControllerId(source.getControllerId());
                specialAction.setSourceId(source.getSourceId());

                // create filter for possible creatures to tap
                FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
                filter.add(Predicates.not(TappedPredicate.instance));
                if (unpaid.getMana().getGeneric() == 0) {
                    List<ColorPredicate> colorPredicates = new ArrayList<>();
                    if (unpaid.getMana().getBlack() > 0) {
                        colorPredicates.add(new ColorPredicate(ObjectColor.BLACK));
                    }
                    if (unpaid.getMana().getBlue() > 0) {
                        colorPredicates.add(new ColorPredicate(ObjectColor.BLUE));
                    }
                    if (unpaid.getMana().getRed() > 0) {
                        colorPredicates.add(new ColorPredicate(ObjectColor.RED));
                    }
                    if (unpaid.getMana().getGreen() > 0) {
                        colorPredicates.add(new ColorPredicate(ObjectColor.GREEN));
                    }
                    if (unpaid.getMana().getWhite() > 0) {
                        colorPredicates.add(new ColorPredicate(ObjectColor.WHITE));
                    }
                    filter.add(Predicates.or(colorPredicates));
                }
                Target target = new TargetControlledCreaturePermanent(1, 1, filter, true);
                target.setTargetName("tap creature card as convoke's pay");
                specialAction.addTarget(target);
                if (specialAction.canActivate(source.getControllerId(), game).canActivate()) {
                    game.getState().getSpecialActions().add(specialAction);
                }
            }
        }
    }

    @Override
    public ManaOptions getManaOptions(Ability source, Game game, ManaCost unpaid) {
        ManaOptions options = new ManaOptions();
        FilterControlledCreaturePermanent filterBasic = new FilterControlledCreaturePermanent();

        // each creature can give {1} or color mana
        game.getBattlefield().getActivePermanents(filterBasic, source.getControllerId(), source.getSourceId(), game)
                .stream()
                .filter(permanent -> !permanent.isTapped())
                .forEach(permanent -> {
                    ManaOptions permMana = new ManaOptions();
                    permMana.add(Mana.GenericMana(1));
                    for (ObjectColor color : permanent.getColor(game).getColors()) {
                        if (color.isBlack()) permMana.add(Mana.BlackMana(1));
                        if (color.isBlue()) permMana.add(Mana.BlueMana(1));
                        if (color.isGreen()) permMana.add(Mana.GreenMana(1));
                        if (color.isRed()) permMana.add(Mana.RedMana(1));
                        if (color.isWhite()) permMana.add(Mana.WhiteMana(1));
                    }
                    options.addMana(permMana);
                });

        options.removeDuplicated();
        return options;
    }
}

class ConvokeSpecialAction extends SpecialAction {

    public ConvokeSpecialAction(ManaCost unpaid, AlternateManaPaymentAbility manaAbility) {
        super(Zone.ALL, manaAbility);
        this.abilityType = AbilityType.SPECIAL_MANA_PAYMENT;
        setRuleVisible(false);
        this.addEffect(new ConvokeEffect(unpaid));
    }

    public ConvokeSpecialAction(final ConvokeSpecialAction ability) {
        super(ability);
    }

    @Override
    public ConvokeSpecialAction copy() {
        return new ConvokeSpecialAction(this);
    }
}

class ConvokeEffect extends OneShotEffect {

    private final ManaCost unpaid;

    public ConvokeEffect(ManaCost unpaid) {
        super(Outcome.Benefit);
        this.unpaid = unpaid;
        this.staticText = "Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color.)";
    }

    public ConvokeEffect(final ConvokeEffect effect) {
        super(effect);
        this.unpaid = effect.unpaid;
    }

    @Override
    public ConvokeEffect copy() {
        return new ConvokeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (controller != null && spell != null) {
            for (UUID creatureId : this.getTargetPointer().getTargets(game, source)) {
                Permanent perm = game.getPermanent(creatureId);
                if (perm == null) {
                    continue;
                }
                String manaName;
                if (!perm.isTapped() && perm.tap(game)) {
                    ManaPool manaPool = controller.getManaPool();
                    Choice chooseManaType = buildChoice(perm.getColor(game), unpaid.getMana());
                    if (!chooseManaType.getChoices().isEmpty()) {
                        if (chooseManaType.getChoices().size() > 1) {
                            chooseManaType.getChoices().add("Colorless");
                            chooseManaType.setMessage("Choose mana color to reduce from " + perm.getName());
                            if (!controller.choose(Outcome.Benefit, chooseManaType, game)) {
                                return false;
                            }
                        } else {
                            chooseManaType.setChoice(chooseManaType.getChoices().iterator().next());
                        }
                        if (chooseManaType.getChoice().equals("Black")) {
                            manaPool.addMana(Mana.BlackMana(1), game, source);
                            manaPool.unlockManaType(ManaType.BLACK);
                        }
                        if (chooseManaType.getChoice().equals("Blue")) {
                            manaPool.addMana(Mana.BlueMana(1), game, source);
                            manaPool.unlockManaType(ManaType.BLUE);
                        }
                        if (chooseManaType.getChoice().equals("Green")) {
                            manaPool.addMana(Mana.GreenMana(1), game, source);
                            manaPool.unlockManaType(ManaType.GREEN);
                        }
                        if (chooseManaType.getChoice().equals("White")) {
                            manaPool.addMana(Mana.WhiteMana(1), game, source);
                            manaPool.unlockManaType(ManaType.WHITE);
                        }
                        if (chooseManaType.getChoice().equals("Red")) {
                            manaPool.addMana(Mana.RedMana(1), game, source);
                            manaPool.unlockManaType(ManaType.RED);
                        }
                        if (chooseManaType.getChoice().equals("Colorless")) {
                            manaPool.addMana(Mana.ColorlessMana(1), game, source);
                            manaPool.unlockManaType(ManaType.COLORLESS);
                        }
                        manaName = chooseManaType.getChoice().toLowerCase(Locale.ENGLISH);
                    } else {
                        manaPool.addMana(Mana.ColorlessMana(1), game, source);
                        manaPool.unlockManaType(ManaType.COLORLESS);
                        manaName = "colorless";
                    }
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.CONVOKED, perm.getId(), source.getSourceId(), source.getControllerId()));
                    game.informPlayers("Convoke: " + controller.getLogName() + " taps " + perm.getLogName() + " to pay one " + manaName + " mana");

                    // can't use mana abilities after that (convoke cost must be payed after mana abilities only)
                    spell.setCurrentActivatingManaAbilitiesStep(ActivationManaAbilityStep.AFTER);
                }
            }
            return true;
        }
        return false;
    }

    private Choice buildChoice(ObjectColor creatureColor, Mana mana) {
        Choice choice = new ChoiceColor();
        choice.getChoices().clear();
        if (creatureColor.isBlack() && mana.getBlack() > 0) {
            choice.getChoices().add("Black");
        }
        if (creatureColor.isBlue() && mana.getBlue() > 0) {
            choice.getChoices().add("Blue");
        }
        if (creatureColor.isGreen() && mana.getGreen() > 0) {
            choice.getChoices().add("Green");
        }
        if (creatureColor.isRed() && mana.getRed() > 0) {
            choice.getChoices().add("Red");
        }
        if (creatureColor.isWhite() && mana.getWhite() > 0) {
            choice.getChoices().add("White");
        }
        return choice;
    }
}
