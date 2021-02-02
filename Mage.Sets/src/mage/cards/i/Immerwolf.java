
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward
 */
public final class Immerwolf extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Wolf and Werewolf creatures");

    static {
        filter.add(Predicates.or(SubType.WOLF.getPredicate(), SubType.WEREWOLF.getPredicate()));
    }

    public Immerwolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}{G}");
        this.subtype.add(SubType.WOLF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(IntimidateAbility.getInstance());

        // Other Wolf and Werewolf creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter, true)));

        // Non-Human Werewolves you control can't transform.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ImmerwolfEffect()));

    }

    private Immerwolf(final Immerwolf card) {
        super(card);
    }

    @Override
    public Immerwolf copy() {
        return new Immerwolf(this);
    }
}

class ImmerwolfEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(SubType.WEREWOLF.getPredicate());
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    public ImmerwolfEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Non-Human Werewolves you control can't transform";
    }

    public ImmerwolfEffect(final ImmerwolfEffect effect) {
        super(effect);
    }

    @Override
    public ImmerwolfEffect copy() {
        return new ImmerwolfEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TRANSFORM;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && 
                permanent.isControlledBy(source.getControllerId()) &&
                filter.match(permanent, game) ;
    }
}
