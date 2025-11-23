package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class CandlesGlow extends CardImpl {

    public CandlesGlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.subtype.add(SubType.ARCANE);


        // Prevent the next 3 damage that would be dealt to any target this turn. You gain life equal to the damage prevented this way.
        this.getSpellAbility().addEffect(new CandlesGlowPreventDamageTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetAnyTarget().withChooseHint("prevent 3 damage"));
        // Splice onto Arcane {1}{W}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{1}{W}"));
    }

    private CandlesGlow(final CandlesGlow card) {
        super(card);
    }

    @Override
    public CandlesGlow copy() {
        return new CandlesGlow(this);
    }
}

class CandlesGlowPreventDamageTargetEffect extends PreventionEffectImpl {

    private int amount = 3;

    public CandlesGlowPreventDamageTargetEffect(Duration duration) {
        super(duration, 3, false);
        staticText = "Prevent the next 3 damage that would be dealt to any target this turn. You gain life equal to the damage prevented this way";
    }

    private CandlesGlowPreventDamageTargetEffect(final CandlesGlowPreventDamageTargetEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public CandlesGlowPreventDamageTargetEffect copy() {
        return new CandlesGlowPreventDamageTargetEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionData = preventDamageAction(event, source, game);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(preventionData.getPreventedDamage(), game, source);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            return source.getTargets().getFirstTarget().equals(event.getTargetId());
        }
        return false;
    }

}
