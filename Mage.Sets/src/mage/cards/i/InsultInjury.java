package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetAndTargetEffect;
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
        getLeftHalfCard().getSpellAbility().addEffect(new DamageCantBePreventedEffect(Duration.EndOfTurn));
        getLeftHalfCard().getSpellAbility().addEffect(new InsultDoubleDamageEffect());

        // to
        // Injury
        // Injury deals 2 damage to target creature and 2 damage to target player.
        getRightHalfCard().addAbility(new AftermathAbility().setRuleAtTheTop(true));
        getRightHalfCard().getSpellAbility().addTarget(new TargetCreaturePermanent().setTargetTag(1));
        getRightHalfCard().getSpellAbility().addTarget(new TargetPlayerOrPlaneswalker().setTargetTag(2));
        getRightHalfCard().getSpellAbility().addEffect(new DamageTargetAndTargetEffect(2, 2));
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

    InsultDoubleDamageEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "If a source you control would deal damage this turn, it deals double that damage instead.";
    }

    private InsultDoubleDamageEffect(final InsultDoubleDamageEffect effect) {
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
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}
