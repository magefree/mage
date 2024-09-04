package mage.cards.f;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Floodgate extends CardImpl {

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ISLAND));
    private static final Hint hint = new ValueHint("Islands you control", xValue);

    public Floodgate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When Floodgate has flying, sacrifice it.
        this.addAbility(new FloodgateHasFlyingStateTriggeredAbility());

        // When Floodgate leaves the battlefield, it deals damage equal to half the number of Islands you control, rounded down, to each nonblue creature without flying.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new FloodgateDamageEffect(), false));
        this.getSpellAbility().addHint(hint);
    }

    private Floodgate(final Floodgate card) {
        super(card);
    }

    @Override
    public Floodgate copy() {
        return new Floodgate(this);
    }
}

class FloodgateHasFlyingStateTriggeredAbility extends StateTriggeredAbility {

    public FloodgateHasFlyingStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    private FloodgateHasFlyingStateTriggeredAbility(final FloodgateHasFlyingStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FloodgateHasFlyingStateTriggeredAbility copy() {
        return new FloodgateHasFlyingStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(getSourceId());
        if (permanent != null && permanent.getAbilities().contains(FlyingAbility.getInstance())) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} has flying, sacrifice it.";
    }

}

class FloodgateDamageEffect extends OneShotEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent();

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLUE)));
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
        filter2.add(SubType.ISLAND.getPredicate());
    }

    public FloodgateDamageEffect() {
        super(Outcome.Benefit);
        this.staticText = "it deals damage equal to half the number of Islands you control, "
                + "rounded down, to each nonblue creature without flying";
    }

    private FloodgateDamageEffect(final FloodgateDamageEffect effect) {
        super(effect);
    }

    @Override
    public FloodgateDamageEffect copy() {
        return new FloodgateDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int islandCount = new PermanentsOnBattlefieldCount(filter2).calculate(game, source, this);
        islandCount = Math.floorDiv(islandCount, 2);
        return new DamageAllEffect(islandCount, filter).apply(game, source);
    }
}
