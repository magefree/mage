package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.target.TargetSource;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class KorDirge extends CardImpl {

    public KorDirge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // All damage that would be dealt this turn to target creature you control by a source of your choice is dealt to another target creature instead.
        this.getSpellAbility().addEffect(new KorDirgeEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_CREATURE_TARGET_2).setTargetTag(2));
    }

    private KorDirge(final KorDirge card) {
        super(card);
    }

    @Override
    public KorDirge copy() {
        return new KorDirge(this);
    }
}

class KorDirgeEffect extends RedirectionEffect {

    protected TargetSource target = new TargetSource();

    KorDirgeEffect() {
        super(Duration.EndOfTurn);
        staticText = "All damage that would be dealt this turn to target creature you control by a source of your choice is dealt to another target creature instead";
    }

    private KorDirgeEffect(final KorDirgeEffect effect) {
        super(effect);
        this.target = effect.target;
    }

    @Override
    public KorDirgeEffect copy() {
        return new KorDirgeEffect(this);
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
