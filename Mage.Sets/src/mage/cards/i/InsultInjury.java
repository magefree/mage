package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.abilities.keyword.AftermathAbility;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Stravant
 */
public final class InsultInjury extends SplitCard {

    public InsultInjury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, new CardType[]{CardType.SORCERY}, "{2}{R}", "{2}{R}", SpellAbilityType.SPLIT_AFTERMATH);

        // Insult
        // Damage can't be prevented this turn. If a source you control would deal damage this turn it deals
        // double that damage instead.
        getLeftHalfCard().getSpellAbility().addEffect(new DamageCantBePreventedEffect(Duration.EndOfTurn, "Damage can't be prevented this turn"));
        getLeftHalfCard().getSpellAbility().addEffect(new InsultDoubleDamageEffect());

        // to
        // Injury
        // Injury deals 2 damage to target creature and 2 damage to target player.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        getRightHalfCard().getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker());
        getRightHalfCard().getSpellAbility().addEffect(new InjuryEffect());
    }

    private InsultInjury(final InsultInjury card) {
        super(card);
    }

    @Override
    public InsultInjury copy() {
        return new InsultInjury(this);
    }
}

class InsultDoubleDamageEffect extends ReplacementEffectImpl {

    public InsultDoubleDamageEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "If a source you control would deal damage this turn, it deals double that damage to that permanent or player instead.";
    }

    public InsultDoubleDamageEffect(final InsultDoubleDamageEffect effect) {
        super(effect);
    }

    @Override
    public InsultDoubleDamageEffect copy() {
        return new InsultDoubleDamageEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                || event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getControllerId(event.getSourceId()).equals(source.getControllerId());
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

class InjuryEffect extends OneShotEffect {

    InjuryEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to target creature and 2 damage to target player";
    }

    InjuryEffect(final InjuryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        Player player = game.getPlayer(source.getTargets().get(1).getFirstTarget());

        if (permanent != null) {
            permanent.damage(2, source.getSourceId(), source, game, false, true);
        }

        if (player != null) {
            player.damage(2, source.getSourceId(), source, game);
        }

        return true;
    }

    @Override
    public InjuryEffect copy() {
        return new InjuryEffect(this);
    }
}
