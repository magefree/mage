package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.SpellsCastWatcher;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShowOfConfidence extends CardImpl {

    public ShowOfConfidence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // When you cast this spell, copy it for each other instant or sorcery spell you've cast this turn. You may choose new targets for the copies.
        this.addAbility(new CastSourceTriggeredAbility(new ShowOfConfidenceEffect()));

        // Put a +1/+1 counter on target creature. It gains vigilance until end of turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains vigilance until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ShowOfConfidence(final ShowOfConfidence card) {
        super(card);
    }

    @Override
    public ShowOfConfidence copy() {
        return new ShowOfConfidence(this);
    }
}

class ShowOfConfidenceEffect extends OneShotEffect {

    ShowOfConfidenceEffect() {
        super(Outcome.Benefit);
        staticText = "copy it for each other instant and sorcery spell you've cast this turn. " +
                "You may choose new targets for the copies";
    }

    private ShowOfConfidenceEffect(final ShowOfConfidenceEffect effect) {
        super(effect);
    }

    @Override
    public ShowOfConfidenceEffect copy() {
        return new ShowOfConfidenceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = (Spell) getValue("spellCast");
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (spell == null || watcher == null) {
            return false;
        }
        int copies = watcher.getSpellsCastThisTurn(source.getControllerId())
                .stream()
                .filter(Objects::nonNull)
                .filter(spell1 -> spell1.isInstantOrSorcery(game))
                .filter(s -> !s.getSourceId().equals(source.getSourceId())
                        || s.getZoneChangeCounter(game) != source.getSourceObjectZoneChangeCounter())
                .mapToInt(x -> 1)
                .sum();
        if (copies > 0) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true, copies);
        }
        return true;
    }
}
