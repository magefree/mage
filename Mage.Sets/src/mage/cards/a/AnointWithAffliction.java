package mage.cards.a;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class AnointWithAffliction extends CardImpl {

    public AnointWithAffliction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Exile target creature if it has mana value 3 or less.
        // Corrupted -- Exile that creature instead if its controller has three or more poison counters.
        this.getSpellAbility().addEffect(new AnointWithAfflictionEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AnointWithAffliction(final AnointWithAffliction card) {
        super(card);
    }

    @Override
    public AnointWithAffliction copy() {
        return new AnointWithAffliction(this);
    }
}

class AnointWithAfflictionEffect extends OneShotEffect {

    AnointWithAfflictionEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature if it has mana value 3 or less.<br>" + AbilityWord.CORRUPTED.formatWord() +
                "Exile that creature instead if its controller has three or more poison counters";
    }

    private AnointWithAfflictionEffect(final AnointWithAfflictionEffect effect) {
        super(effect);
    }

    @Override
    public AnointWithAfflictionEffect copy() {
        return new AnointWithAfflictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return player != null && permanent != null
                && (permanent.getManaValue() <= 3
                || Optional
                .ofNullable(game.getPlayer(permanent.getControllerId()))
                .filter(Objects::nonNull)
                .map(Player::getCounters)
                .map(counters -> counters.getCount(CounterType.POISON) >= 3)
                .orElse(false))
                && player.moveCards(permanent, Zone.EXILED, source, game);
    }
}
