package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.Counter;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class BackstreetBruiser extends CardImpl {

    public BackstreetBruiser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // As long as there are two or more counters among creatures you control, Backstreet Bruiser can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield),
                BackstreetBruiserCondition.instance
        ).setText("As long as there are two or more counters among creatures you control, {this} can attack as though it didn't have defender")));
    }

    private BackstreetBruiser(final BackstreetBruiser card) {
        super(card);
    }

    @Override
    public BackstreetBruiser copy() {
        return new BackstreetBruiser(this);
    }
}

enum BackstreetBruiserCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        int totalCounters = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game)) {
            for (Counter counter : permanent.getCounters(game).values()) {
                totalCounters += counter.getCount();
                if (totalCounters >= 2) {
                    return true;
                }
            }
        }
        return false;
    }
}
