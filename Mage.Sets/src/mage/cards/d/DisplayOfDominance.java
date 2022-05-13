
package mage.cards.d;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class DisplayOfDominance extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("blue or black noncreature permanent");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.BLACK)));
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public DisplayOfDominance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);

        // Destroy target blue or black noncreature permanent
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // or Permanents you control can't be the targets of blue or black spells your opponents control this turn
        Mode mode = new Mode(new DisplayOfDominanceEffect());
        this.getSpellAbility().getModes().addMode(mode);
    }

    private DisplayOfDominance(final DisplayOfDominance card) {
        super(card);
    }

    @Override
    public DisplayOfDominance copy() {
        return new DisplayOfDominance(this);
    }
}

class DisplayOfDominanceEffect extends ContinuousRuleModifyingEffectImpl {

    public DisplayOfDominanceEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "permanents you control can't be the targets of blue or black spells your opponents control this turn";
    }

    public DisplayOfDominanceEffect(final DisplayOfDominanceEffect effect) {
        super(effect);
    }

    @Override
    public DisplayOfDominanceEffect copy() {
        return new DisplayOfDominanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability ability, Game game) {
        MageObject mageObject = game.getObject(event.getSourceId());
        if (game.getPlayer(ability.getControllerId()).hasOpponent(event.getPlayerId(), game) &&
                mageObject instanceof Spell &&
                (mageObject.getColor(game).isBlack() || mageObject.getColor(game).isBlue())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            return permanent != null && permanent.isControlledBy(ability.getControllerId());
        }
        return false;
    }
}
