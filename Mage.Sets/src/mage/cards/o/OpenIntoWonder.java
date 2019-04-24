
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OpenIntoWonder extends CardImpl {

    public OpenIntoWonder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // X target creatures can't be blocked this turn. Until end of turn, those creatures gain "Whenever this creature deals combat damage to a player, draw a card."
        Effect effect = new CantBeBlockedTargetEffect(Duration.EndOfTurn);
        effect.setText("X target creatures can't be blocked this turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1, StaticFilters.FILTER_PERMANENT_CREATURE, false));
        Ability abilityToGain = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1), false);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(abilityToGain, Duration.EndOfTurn,
                "Until end of turn, those creatures gain \"Whenever this creature deals combat damage to a player, draw a card.\""));
    }

    public OpenIntoWonder(final OpenIntoWonder card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int numberOfTargets = ability.getManaCostsToPay().getX();
            numberOfTargets = Math.min(game.getBattlefield().count(StaticFilters.FILTER_PERMANENT_CREATURE, ability.getSourceId(), ability.getControllerId(), game), numberOfTargets);
            ability.addTarget(new TargetCreaturePermanent(numberOfTargets));
        }
    }

    @Override
    public OpenIntoWonder copy() {
        return new OpenIntoWonder(this);
    }
}
