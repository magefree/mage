package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class Overblaze extends CardImpl {

    public Overblaze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");
        this.subtype.add(SubType.ARCANE);

        // Each time target permanent would deal damage to a creature or player this turn, it deals double that damage to that creature or player instead.
        this.getSpellAbility().addEffect(new OverblazeEffect());
        this.getSpellAbility().addTarget(new TargetPermanent().withChooseHint("deals double damage"));
        // Splice onto Arcane {2}{R}{R}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{2}{R}{R}"));
    }

    private Overblaze(final Overblaze card) {
        super(card);
    }

    @Override
    public Overblaze copy() {
        return new Overblaze(this);
    }
}

class OverblazeEffect extends ReplacementEffectImpl {

    public OverblazeEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "Each time target permanent would deal damage to a permanent or player this turn, it deals double that damage to that permanent or player instead.";
    }

    public OverblazeEffect(final OverblazeEffect effect) {
        super(effect);
    }

    @Override
    public OverblazeEffect copy() {
        return new OverblazeEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getSourceId().equals(this.getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }

}
