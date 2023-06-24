package mage.cards.d;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetWithReplacementEffect;
import mage.abilities.effects.common.continuous.GainSuspendEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PutCards;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
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

    public DelayEffect() {
        super(Outcome.Benefit);
        this.staticText = "Counter target spell. If the spell is countered this way, exile it with three time counters on it instead of putting it into its owner's graveyard. If it doesn't have suspend, it gains suspend";
    }

    public DelayEffect(final DelayEffect effect) {
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
        if (controller != null && spell != null) {
            Effect effect = new CounterTargetWithReplacementEffect(PutCards.EXILED);
            effect.setTargetPointer(targetPointer);
            Card card = game.getCard(spell.getSourceId());
            if (card != null && effect.apply(game, source) && game.getState().getZone(card.getId()) == Zone.EXILED) {
                boolean hasSuspend = card.getAbilities(game).containsClass(SuspendAbility.class);
                UUID exileId = SuspendAbility.getSuspendExileId(controller.getId(), game);
                if (controller.moveCardToExileWithInfo(card, exileId, "Suspended cards of " + controller.getLogName(), source, game, Zone.HAND, true)) {
                    card.addCounters(CounterType.TIME.createInstance(3), source.getControllerId(), source, game);
                    if (!hasSuspend) {
                        game.addEffect(new GainSuspendEffect(new MageObjectReference(card, game)), source);
                    }
                    game.informPlayers(controller.getLogName() + " suspends 3 - " + card.getName());
                }
            }
            return true;
        }
        return false;
    }
}
