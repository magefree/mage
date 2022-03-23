
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class KorChant extends CardImpl {

    public KorChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // All damage that would be dealt this turn to target creature you control by a source of your choice is dealt to another target creature instead.
        this.getSpellAbility().addEffect(new KorChantEffect());
        TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
        target.setTargetTag(1);
        this.getSpellAbility().addTarget(target);

        FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");
        filter.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);
    }

    private KorChant(final KorChant card) {
        super(card);
    }

    @Override
    public KorChant copy() {
        return new KorChant(this);
    }
}

class KorChantEffect extends RedirectionEffect {

    protected TargetSource target = new TargetSource();

    KorChantEffect() {
        super(Duration.EndOfTurn);
        staticText = "All damage that would be dealt this turn to target creature you control by a source of your choice is dealt to another target creature instead";
    }

    KorChantEffect(final KorChantEffect effect) {
        super(effect);
        this.target = effect.target;
    }

    @Override
    public KorChantEffect copy() {
        return new KorChantEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getTargetId().equals(this.getTargetPointer().getFirst(game, source))
                && event.getSourceId().equals(this.target.getFirstTarget())) {
            this.redirectTarget = source.getTargets().get(1);
            return true;
        }
        return false;
    }
}
