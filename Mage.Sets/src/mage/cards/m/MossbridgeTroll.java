
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class MossbridgeTroll extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped creatures you control");

    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public MossbridgeTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.TROLL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // If Mossbridge Troll would be destroyed, regenerate it.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new MossbridgeTrollReplacementEffect()));

        // Tap any number of untapped creatures you control other than Mossbridge Troll with total power 10 or greater: Mossbridge Troll gets +20/+20 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(20, 20, Duration.EndOfTurn), new MossbridgeTrollCost());
        ability.setAdditionalCostsRuleVisible(false);
        this.addAbility(ability);

    }

    public MossbridgeTroll(final MossbridgeTroll card) {
        super(card);
    }

    @Override
    public MossbridgeTroll copy() {
        return new MossbridgeTroll(this);
    }
}

class MossbridgeTrollReplacementEffect extends ReplacementEffectImpl {

    MossbridgeTrollReplacementEffect() {
        super(Duration.Custom, Outcome.Regenerate);
        staticText = "If {this} would be destroyed, regenerate it";
    }

    MossbridgeTrollReplacementEffect(MossbridgeTrollReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent mossbridgeTroll = game.getPermanent(event.getTargetId());
        if (mossbridgeTroll != null && event.getAmount() == 0) { // 1=noRegen
            return mossbridgeTroll.regenerate(source.getSourceId(), game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId() != null
                && event.getTargetId().equals(source.getSourceId());
    }

    @Override
    public MossbridgeTrollReplacementEffect copy() {
        return new MossbridgeTrollReplacementEffect(this);
    }

}

class MossbridgeTrollCost extends CostImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("any number of untapped creatures other than {this} with total power 10 or greater");

    static {
        filter.add(new AnotherPredicate());
        filter.add(Predicates.not(new TappedPredicate()));
    }

    public MossbridgeTrollCost() {
        this.addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true));
        this.text = "tap any number of untapped creatures you control other than {this} with total power 10 or greater";
    }

    public MossbridgeTrollCost(final MossbridgeTrollCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        int sumPower = 0;
        if (targets.choose(Outcome.Tap, controllerId, sourceId, game)) {
            for (UUID targetId : targets.get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && permanent.tap(game)) {
                    sumPower += permanent.getPower().getValue();
                }
            }
        }
        game.informPlayers(new StringBuilder("Tap creatures with total power of ").append(sumPower).toString());
        paid = sumPower >= 10;
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        int sumPower = 0;
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, controllerId, game)) {
            if (!permanent.getId().equals(sourceId)) {
                sumPower += permanent.getPower().getValue();
            }
        }
        return sumPower >= 10;
    }

    @Override
    public MossbridgeTrollCost copy() {
        return new MossbridgeTrollCost(this);
    }
}
