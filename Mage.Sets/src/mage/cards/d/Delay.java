package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Delay extends CardImpl {

    public Delay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target spell. If the spell is countered this way, exile it with three time counters on it instead of putting it into its owner's graveyard. If it doesn't have suspend, it gains suspend.
        this.getSpellAbility().addEffect(new DelayEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private Delay(final Delay card) {
        super(card);
    }

    @Override
    public Delay copy() {
        return new Delay(this);
    }
}

class DelayEffect extends OneShotEffect {

    DelayEffect() {
        super(Outcome.Benefit);
        this.staticText = "counter target spell. If the spell is countered this way, exile it with three time counters " +
                "on it instead of putting it into its owner's graveyard. If it doesn't have suspend, it gains suspend";
    }

    private DelayEffect(final DelayEffect effect) {
        super(effect);
    }

    @Override
    public DelayEffect copy() {
        return new DelayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        return controller != null
                && spell != null
                && game.getStack().counter(spell.getId(), source, game, PutCards.EXILED)
                && SuspendAbility.addTimeCountersAndSuspend(spell.getMainCard(), 3, source, game);
    }
}
