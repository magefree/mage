
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.EffectType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class GroundlingPouncer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public GroundlingPouncer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/U}");
        this.subtype.add(SubType.FAERIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {GU}: Groundling Pouncer gets +1/+3 and gains flying until end of turn. Activate this ability only once each turn and only if an opponent controls a creature with flying.
        Ability ability = new GroundlingPouncerAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(1, 3, Duration.EndOfTurn),
                new ManaCostsImpl<>("{G/U}"),
                new OpponentControlsPermanentCondition(filter),
                "{G/U}: {this} gets +1/+3 and gains flying until end of turn. Activate only once each turn and only if an opponent controls a creature with flying.");
        ability.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, false, true));
        this.addAbility(ability);

    }

    private GroundlingPouncer(final GroundlingPouncer card) {
        super(card);
    }

    @Override
    public GroundlingPouncer copy() {
        return new GroundlingPouncer(this);
    }
}

class GroundlingPouncerAbility extends LimitedTimesPerTurnActivatedAbility {

    private static final Effects emptyEffects = new Effects();

    private final String ruleText;

    public GroundlingPouncerAbility(Zone zone, Effect effect, Cost cost, Condition condition, String rule) {
        super(zone, effect, cost);
        this.condition = condition;
        this.ruleText = rule;
    }

    public GroundlingPouncerAbility(GroundlingPouncerAbility ability) {
        super(ability);
        this.ruleText = ability.ruleText;
    }

    @Override
    public Effects getEffects(Game game, EffectType effectType) {
        if (!condition.apply(game, this)) {
            return emptyEffects;
        }
        return super.getEffects(game, effectType);
    }

    @Override
    public GroundlingPouncerAbility copy() {
        return new GroundlingPouncerAbility(this);
    }

    @Override
    public String getRule() {
        return ruleText;
    }
}
