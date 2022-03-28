
package mage.cards.b;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 *
 * @author L_J
 */
public final class BINGO extends CardImpl {

    public BINGO(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever a player casts a spell, put a chip counter on its converted mana cost.
        this.addAbility(new SpellCastAllTriggeredAbility(new BingoEffect(), StaticFilters.FILTER_SPELL_A, false, SetTargetPointer.SPELL));

        // B-I-N-G-O gets +9/+9 for each set of three numbers in a row with chip counters on them.
        BingoCount count = new BingoCount();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostSourceEffect(count, count, Duration.WhileOnBattlefield)));
    }

    private BINGO(final BINGO card) {
        super(card);
    }

    @Override
    public BINGO copy() {
        return new BINGO(this);
    }
}

class BingoEffect extends OneShotEffect {

    public BingoEffect() {
        super(Outcome.Neutral);
        staticText = "put a chip counter on its mana value";
    }

    public BingoEffect(final BingoEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            if (spell.getManaValue() > 9) {
                return true;
            }
            MageObject mageObject = game.getObject(source);
            if (mageObject != null) {
                Map<Integer, Integer> chipCounters = new HashMap<>(); // Map<number, amount of counters>
                if (game.getState().getValue(mageObject.getId() + "_chip") != null) {
                    chipCounters.putAll((Map<Integer, Integer>) game.getState().getValue(mageObject.getId() + "_chip"));
                }
                chipCounters.putIfAbsent(spell.getManaValue(), 0);
                chipCounters.put(spell.getManaValue(), chipCounters.get(spell.getManaValue()) + 1);
                game.getState().setValue(mageObject.getId() + "_chip", chipCounters);
                if (mageObject instanceof Permanent) {
                    StringBuilder sb = new StringBuilder();
                    int i = 0;
                    for (Map.Entry<Integer, Integer> entry : chipCounters.entrySet()) {
                        i++;
                        sb.append(entry.getKey());
                        if (i < chipCounters.size()) {
                            sb.append(", ");
                        }
                    }
                    ((Permanent) mageObject).addInfo("chip counters", CardUtil.addToolTipMarkTags("Chip counters at: " + sb), game);
                    new AddCountersSourceEffect(CounterType.CHIP.createInstance()).apply(game, source);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public BingoEffect copy() {
        return new BingoEffect(this);
    }
}

class BingoCount implements DynamicValue {

    public BingoCount() {
    }

    public BingoCount(final BingoCount countersCount) {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        MageObject mageObject = game.getObject(sourceAbility.getSourceId());
        if (mageObject instanceof Permanent) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(sourceAbility.getSourceId());
            if (permanent != null && game.getState().getValue(mageObject.getId() + "_chip") != null) {
                int rows = 0;
                Set<Integer> nums = ((Map<Integer, Integer>) game.getState().getValue(mageObject.getId() + "_chip")).keySet();
                // if (nums.size() <= permanent.getCounters(game).getCount(CounterType.CHIP)) {
                    
                    // 1 4 7
                    // 8 5 3
                    // 2 0 6
                    
                    if (nums.contains(1) && nums.contains(4) && nums.contains(7)) {
                        rows++;
                    }
                    if (nums.contains(1) && nums.contains(8) && nums.contains(2)) {
                        rows++;
                    }
                    if (nums.contains(1) && nums.contains(5) && nums.contains(6)) {
                        rows++;
                    }
                    if (nums.contains(8) && nums.contains(5) && nums.contains(3)) {
                        rows++;
                    }
                    if (nums.contains(4) && nums.contains(5) && nums.contains(0)) {
                        rows++;
                    }
                    if (nums.contains(2) && nums.contains(0) && nums.contains(6)) {
                        rows++;
                    }
                    if (nums.contains(7) && nums.contains(3) && nums.contains(6)) {
                        rows++;
                    }
                    if (nums.contains(2) && nums.contains(5) && nums.contains(7)) {
                        rows++;
                    }
                    return rows * 9;
                // }
            }
        }
        return 0;
    }

    @Override
    public BingoCount copy() {
        return new BingoCount(this);
    }

    @Override
    public String toString() {
        return "9";
    }

    @Override
    public String getMessage() {
        return "set of three numbers in a row with chip counters on them";
    }
}
