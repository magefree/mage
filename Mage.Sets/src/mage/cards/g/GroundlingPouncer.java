package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effects;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class GroundlingPouncer extends CardImpl {

    public GroundlingPouncer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/U}");
        this.subtype.add(SubType.FAERIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {G/U}: Groundling Pouncer gets +1/+3 and gains flying until end of turn. Activate this ability only once each turn and only if an opponent controls a creature with flying.
        this.addAbility(new GroundlingPouncerAbility());

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

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    private static final Effects emptyEffects = new Effects();

    private static final String ruleText = "{G/U}: {this} gets +1/+3 and gains flying until end of turn. Activate only once each turn and only if an opponent controls a creature with flying.";

    GroundlingPouncerAbility() {
        super(Zone.BATTLEFIELD, new BoostSourceEffect(1, 3, Duration.EndOfTurn), new ManaCostsImpl<>("{G/U}"));
        this.condition = new OpponentControlsPermanentCondition(filter);
        this.addEffect(new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn));
    }

    private GroundlingPouncerAbility(final GroundlingPouncerAbility ability) {
        super(ability);
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
