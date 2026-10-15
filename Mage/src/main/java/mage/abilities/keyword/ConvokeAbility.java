package mage.abilities.keyword;

import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpecialAction;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.*;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.mana.ManaOptions;
import mage.constants.AbilityType;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.util.CardUtil;
import mage.util.ManaUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 702.51. Convoke
 * <p>
 * 702.51a Convoke is a static ability that functions while the spell with convoke is on the stack. "Convoke" means
 * "For each colored mana in this spell's total cost, you may tap an untapped creature of that color you control
 * rather than pay that mana. For each generic mana in this spell's total cost, you may tap an untapped creature you
 * control rather than pay that mana."
 * <p>
 * 702.51b The convoke ability isn't an additional or alternative cost and applies only after the total cost of the
 * spell with convoke is determined.
 * <p>
 * 702.51c A creature tapped to pay for mana in a spell's total cost this way is said to have "convoked" that spell.
 * <p>
 * 702.51d Multiple instances of convoke on the same spell are redundant.
 *
 * @author LevelX2, JayDi85, notgreat
 */
public class ConvokeAbility extends SimpleStaticAbility implements AlternateManaPaymentAbility {

    public static final String convokingCreaturesKey = "convokingCreatures";

    public ConvokeAbility() {
        super(Zone.ALL, null); // all AlternateManaPaymentAbility must use ALL zone to calculate playable abilities
        this.setRuleAtTheTop(true);
        this.addHint(new ValueHint("Untapped creatures you control",
                new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE)));
    }

    protected ConvokeAbility(final ConvokeAbility ability) {
        super(ability);
    }

    @Override
    public ConvokeAbility copy() {
        return new ConvokeAbility(this);
    }

    @Override
    public String getRule() {
        return "convoke <i>(Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color.)</i>";
    }

    @Override
    public ActivationManaAbilityStep useOnActivationManaAbilityStep() {
        return ActivationManaAbilityStep.AFTER;
    }

    @Override
    public void addSpecialAction(Ability source, Game game, ManaCost paying) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null
                || source.getAbilityType() != AbilityType.SPELL
                || !game.getBattlefield().containsControlled(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE, source, game, 1)) {
            return;
        }
        int maxCreatures = new ConvokePayment(paying).getMaxCreatures();
        if (maxCreatures == 0) {
            return;
        }
        SpecialAction specialAction = new ConvokeSpecialAction(paying, this);
        specialAction.setControllerId(source.getControllerId());
        specialAction.setSourceId(source.getSourceId());
        specialAction.addTarget(new ConvokeTarget(maxCreatures, paying));
        if (specialAction.canActivate(source.getControllerId(), game).canActivate()) {
            game.getState().getSpecialActions().add(specialAction);
        }
    }

    @Override
    public ManaOptions getManaOptions(Ability source, Game game, ManaCost unpaid) {
        ManaOptions options = new ManaOptions();

        // each creature can give {1} or color mana
        game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE, source.getControllerId(), source, game)
                .forEach(permanent -> {
                    ManaOptions permMana = new ManaOptions();
                    permMana.add(Mana.GenericMana(1));
                    for (ManaType color : ConvokePayment.colorsOf(permanent, game)) {
                        permMana.add(new Mana(color));
                    }
                    options.addMana(permMana);
                });

        options.removeFullyIncludedVariations();
        return options;
    }
}

/**
 * Works out which symbol each tapped creature pays (702.51a), so a whole group can be tapped at once.
 * <p>
 * Each creature pays one colored symbol it shares a color with, or one generic. A maximum matching of creatures to
 * colored symbols leaves the fewest creatures to pay generic, so a group can pay together iff those fit in the
 * generic left. A {2/W} is either a colored symbol or two generic, so every choice of which are paid by color is
 * tried (at most 2^5, for Reaper King).
 */
class ConvokePayment {

    private final ManaCost paying;
    private final List<ManaCost> symbolCosts = new ArrayList<>(); // the colored symbols to be paid
    private final List<Set<ManaType>> symbolColors = new ArrayList<>(); // colors that can pay each symbol
    private final List<Integer> genericAlternatives = new ArrayList<>(); // a {2/W}'s generic amount, 0 for any other
    private int generic = 0; // includes what's left of a partly paid {2/W}
    // the order symbols are matched in, which decides what's left over: fewest kinds of mana first (COLORLESS counts
    // for {C/W}), so {W} is paid before {W/U} or {C/W}, leaving those to mana that can pay them more ways; {2/W} last,
    // since generic can also pay it
    private final int[] symbolOrder;
    private final int[] monoHybrids; // the {2/W} symbols

    ConvokePayment(ManaCost paying) {
        this.paying = paying;
        for (ManaCost part : ManaUtil.getUnpaidParts(paying)) {
            addSymbol(part);
        }
        this.symbolOrder = IntStream.range(0, symbolColors.size())
                .boxed()
                .sorted(Comparator.comparing((Integer symbol) -> genericAlternatives.get(symbol) > 0)
                        .thenComparing(symbol -> symbolColors.get(symbol).size()))
                .mapToInt(Integer::intValue)
                .toArray();
        this.monoHybrids = IntStream.range(0, genericAlternatives.size())
                .filter(symbol -> genericAlternatives.get(symbol) > 0)
                .toArray();
    }

    private void addSymbol(ManaCost part) {
        int unpaidGeneric = ManaUtil.getUnpaidGenericOfPart(part);
        if (ManaUtil.isGenericOnly(part)) {
            generic += unpaidGeneric;
        } else if (part instanceof ColoredManaCost || part instanceof HybridManaCost || part instanceof ColorlessHybridManaCost) {
            // 107.4e: "A hybrid mana symbol is also a colored mana symbol, even if one of its components is colorless."
            symbolColors.add(ManaType.getManaTypesFromManaList(part.getMana()));
            genericAlternatives.add(0);
            symbolCosts.add(part);
        } else if (part instanceof MonoHybridManaCost) {
            symbolColors.add(ManaType.getManaTypesFromManaList(new Mana(((MonoHybridManaCost) part).getManaColor())));
            genericAlternatives.add(unpaidGeneric);
            symbolCosts.add(part);
        }
        // {C} and snow can't be paid by convoke
    }

    int getMaxCreatures() {
        // each colored symbol takes one creature, a {2/W} paid as generic as many as its generic amount
        return generic + symbolColors.size() + genericAlternatives.stream().mapToInt(amount -> Math.max(amount - 1, 0)).sum();
    }

    /**
     * @param creatureColors the colors of each creature, see {@link #colorsOf}
     * @return whether each of these creatures can pay for something alongside the others
     */
    boolean canPay(List<Set<ManaType>> creatureColors) {
        return IntStream.range(0, 1 << monoHybrids.length).anyMatch(byColor -> match(creatureColors, byColor) != null);
    }

    /**
     * Matches creatures to as many colored symbols as they can pay; the rest pay generic. A symbol is left unpaid
     * only if it can't be paid alongside the ones before it in symbolOrder.
     *
     * @return null if some creature would pay for nothing
     */
    Assignment assign(List<Set<ManaType>> creatureColors) {
        // keep the choice that pays the most colored symbols, since those are harder to pay with mana
        int[] best = null;
        int bestMatched = -1;
        for (int byColor = 0; byColor < 1 << monoHybrids.length; byColor++) {
            int[] symbolOf = match(creatureColors, byColor);
            if (symbolOf == null) {
                continue;
            }
            int matched = (int) Arrays.stream(symbolOf).filter(symbol -> symbol >= 0).count();
            if (matched > bestMatched) {
                best = symbolOf;
                bestMatched = matched;
            }
        }
        return best == null ? null : new Assignment(best, creatureColors);
    }

    /**
     * @param byColor bit i set: monoHybrids[i] is paid by a creature of its color, otherwise as generic
     * @return symbolOf[creature] is the colored symbol that creature pays, or -1 for generic; null if some creature
     * would pay for nothing
     */
    private int[] match(List<Set<ManaType>> creatureColors, int byColor) {
        List<Set<ManaType>> payableColors = new ArrayList<>(symbolColors);
        int capacity = generic;
        for (int i = 0; i < monoHybrids.length; i++) {
            if ((byColor & 1 << i) == 0) {
                // paid as generic: no creature can pay it by color, and unmatched creatures can pay its generic
                payableColors.set(monoHybrids[i], Collections.emptySet());
                capacity += genericAlternatives.get(monoHybrids[i]);
            }
        }
        int[] symbolOf = new int[creatureColors.size()];
        Arrays.fill(symbolOf, -1);
        for (int symbol : symbolOrder) {
            augment(symbol, creatureColors, payableColors, symbolOf, new boolean[creatureColors.size()]);
        }
        long unmatched = Arrays.stream(symbolOf).filter(symbol -> symbol < 0).count();
        // every unmatched creature pays generic, and no other matching leaves fewer of them
        return unmatched <= capacity ? symbolOf : null;
    }

    void pay(Assignment assignment) {
        int genericCreatures = 0;
        for (int creature = 0; creature < assignment.symbolOf.length; creature++) {
            ManaCost symbol = assignment.symbolOf[creature];
            if (symbol == null) {
                genericCreatures++;
            } else {
                symbol.setPayment(new Mana(assignment.manaTypes[creature]));
            }
        }
        ManaUtil.payGeneric(paying, genericCreatures, null);
    }

    /**
     * Kuhn's augmenting path: finds a creature to pay the symbol, moving already matched symbols to other creatures if
     * that frees one. A matched symbol stays matched.
     */
    private boolean augment(int symbol, List<Set<ManaType>> creatureColors, List<Set<ManaType>> payableColors,
                            int[] symbolOf, boolean[] visited) {
        for (int creature = 0; creature < creatureColors.size(); creature++) {
            if (visited[creature] || Collections.disjoint(creatureColors.get(creature), payableColors.get(symbol))) {
                continue;
            }
            visited[creature] = true;
            if (symbolOf[creature] < 0
                    || augment(symbolOf[creature], creatureColors, payableColors, symbolOf, visited)) {
                symbolOf[creature] = symbol;
                return true;
            }
        }
        return false;
    }

    static List<Set<ManaType>> colorsOf(List<Permanent> creatures, Game game) {
        return creatures.stream().map(creature -> colorsOf(creature, game)).collect(Collectors.toList());
    }

    static Set<ManaType> colorsOf(Permanent creature, Game game) {
        return ManaType.getManaTypesFromManaList(Mana.fromColor(creature.getColor(game)));
    }

    class Assignment {

        private final ManaCost[] symbolOf; // for each creature, the colored symbol it pays, or null for generic
        private final ManaType[] manaTypes; // for each creature, the color it pays with, or GENERIC

        private Assignment(int[] matching, List<Set<ManaType>> creatureColors) {
            this.symbolOf = new ManaCost[matching.length];
            this.manaTypes = new ManaType[matching.length];
            Arrays.fill(manaTypes, ManaType.GENERIC);
            for (int creature = 0; creature < matching.length; creature++) {
                int symbol = matching[creature];
                if (symbol >= 0) {
                    symbolOf[creature] = symbolCosts.get(symbol);
                    // a creature sharing both colors of a hybrid pays with the first in ManaType order; either pays it
                    Set<ManaType> colors = EnumSet.copyOf(creatureColors.get(creature));
                    colors.retainAll(symbolColors.get(symbol));
                    manaTypes[creature] = colors.iterator().next();
                }
            }
        }

        ManaType getManaType(int creature) {
            return manaTypes[creature];
        }
    }
}

/**
 * Offers only the creatures that can still pay for something alongside the ones already chosen
 */
class ConvokeTarget extends TargetPermanent {

    private final ManaCost paying; // not copied, see AlternateManaPaymentAbility.addSpecialAction

    ConvokeTarget(int maxCreatures, ManaCost paying) {
        super(1, maxCreatures, StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE, true);
        this.paying = paying;
        withTargetName("creature to tap for convoke");
    }

    private ConvokeTarget(final ConvokeTarget target) {
        super(target);
        this.paying = target.paying;
    }

    @Override
    public ConvokeTarget copy() {
        return new ConvokeTarget(this);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = super.possibleTargets(sourceControllerId, source, game);
        ConvokePayment payment = new ConvokePayment(paying);
        List<Set<ManaType>> chosen = ConvokePayment.colorsOf(this.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()), game);
        Set<ManaType> colorless = EnumSet.noneOf(ManaType.class);
        if (canPayAlongside(payment, chosen, colorless)) {
            // a colorless creature can only pay generic, so if one fits, so does any creature
            return possibleTargets;
        }
        possibleTargets.removeIf(id -> !canPayAlongside(payment, chosen, ConvokePayment.colorsOf(game.getPermanent(id), game)));
        return possibleTargets;
    }

    private static boolean canPayAlongside(ConvokePayment payment, List<Set<ManaType>> chosen, Set<ManaType> colors) {
        List<Set<ManaType>> creatureColors = new ArrayList<>(chosen);
        creatureColors.add(colors);
        return payment.canPay(creatureColors);
    }
}

class ConvokeSpecialAction extends SpecialAction {

    public ConvokeSpecialAction(ManaCost paying, AlternateManaPaymentAbility manaAbility) {
        super(Zone.ALL, manaAbility);
        this.abilityType = AbilityType.SPECIAL_MANA_PAYMENT;
        setRuleVisible(false);
        this.addEffect(new ConvokeEffect(paying));
    }

    protected ConvokeSpecialAction(final ConvokeSpecialAction ability) {
        super(ability);
    }

    @Override
    public ConvokeSpecialAction copy() {
        return new ConvokeSpecialAction(this);
    }
}

class ConvokeEffect extends OneShotEffect {

    private final ManaCost paying; // not copied, see AlternateManaPaymentAbility.addSpecialAction

    public ConvokeEffect(ManaCost paying) {
        super(Outcome.Benefit);
        this.paying = paying;
        this.staticText = "Convoke (Your creatures can help cast this spell. Each creature you tap while casting this spell pays for {1} or one mana of that creature's color.)";
    }

    protected ConvokeEffect(final ConvokeEffect effect) {
        super(effect);
        this.paying = effect.paying;
    }

    @Override
    public ConvokeEffect copy() {
        return new ConvokeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(source.getSourceId());
        if (controller == null || spell == null) {
            return false;
        }
        ConvokePayment payment = new ConvokePayment(paying);
        Map<Permanent, Set<ManaType>> creatures = new LinkedHashMap<>();
        for (UUID creatureId : this.getTargetPointer().getTargets(game, source)) {
            Permanent perm = game.getPermanent(creatureId);
            if (perm != null && !perm.isTapped()) {
                creatures.put(perm, ConvokePayment.colorsOf(perm, game));
            }
        }
        // ConvokeTarget offers only creatures that can pay together, but Target.add doesn't check
        if (!payment.canPay(new ArrayList<>(creatures.values()))) {
            return false;
        }
        creatures.keySet().removeIf(perm -> !perm.tap(source, game));
        if (creatures.isEmpty()) {
            return true;
        }

        List<Permanent> tapped = new ArrayList<>(creatures.keySet());
        // not null: any subset of a group that can pay can still pay, so those that tapped can
        ConvokePayment.Assignment assignment = payment.assign(new ArrayList<>(creatures.values()));
        payment.pay(assignment);
        SpellAbility spellAbility = spell.getSpellAbility();
        HashSet<MageObjectReference> convokingCreatures = CardUtil.getSourceCostsTag(game, spellAbility, ConvokeAbility.convokingCreaturesKey, new HashSet<>());
        for (int creature = 0; creature < tapped.size(); creature++) {
            Permanent perm = tapped.get(creature);
            convokingCreatures.add(new MageObjectReference(perm, game));
            if (!game.isSimulation()) {
                game.informPlayers("Convoke: " + controller.getLogName() + " taps " + perm.getLogName() + " to pay " + new Mana(assignment.getManaType(creature)));
            }
        }
        spellAbility.setCostsTag(ConvokeAbility.convokingCreaturesKey, convokingCreatures);

        // can't use mana abilities after that (convoke cost must be paid after mana abilities only)
        spell.setCurrentActivatingManaAbilitiesStep(ActivationManaAbilityStep.AFTER);
        return true;
    }
}
