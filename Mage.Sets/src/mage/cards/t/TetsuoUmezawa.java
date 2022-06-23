
package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author JRHerlehy
 */
public final class TetsuoUmezawa extends CardImpl {

    private static final FilterCreaturePermanent creatureFilter = new FilterCreaturePermanent("tapped or blocking creature");

    static {
        creatureFilter.add(Predicates.or(
                TappedPredicate.TAPPED,
                BlockingPredicate.instance));
    }

    public TetsuoUmezawa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}{B}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Tetsuo Umezawa can't be the target of Aura spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TetsuoUmezawaEffect()));
        // {U}{B}{B}{R}, {tap}: Destroy target tapped or blocking creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl<>("{U}{B}{B}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(creatureFilter));
        this.addAbility(ability);
    }

    private TetsuoUmezawa(final TetsuoUmezawa card) {
        super(card);
    }

    @Override
    public TetsuoUmezawa copy() {
        return new TetsuoUmezawa(this);
    }
}

class TetsuoUmezawaEffect extends ContinuousRuleModifyingEffectImpl {

    public TetsuoUmezawaEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "{this} can't be the target of Aura spells";
    }

    public TetsuoUmezawaEffect(final TetsuoUmezawaEffect effect) {
        super(effect);
    }

    @Override
    public TetsuoUmezawaEffect copy() {
        return new TetsuoUmezawaEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            return sourcePermanent.getLogName() + " can't be the target of Aura spells";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        if (stackObject != null && event.getTargetId().equals(source.getSourceId())) {
            if (stackObject.hasSubtype(SubType.AURA, game)) {
                return true;
            }
        }
        return false;
    }
}
