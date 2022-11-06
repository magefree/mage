package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterAttackingOrBlockingCreature;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Dilnu
 */
public final class BorosFuryShield extends CardImpl {

    private static final FilterAttackingOrBlockingCreature filter = new FilterAttackingOrBlockingCreature();

    public BorosFuryShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Prevent all combat damage that would be dealt by target attacking or blocking creature this turn.
        this.getSpellAbility().addEffect(new PreventDamageByTargetEffect(Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

        // If {R} was spent to cast Boros Fury-Shield, it deals damage to that creature's controller equal to the creature's power.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new BorosFuryShieldDamageEffect(),
                ManaWasSpentCondition.RED, "If {R} was spent to cast this spell, it deals damage to that creature's controller equal to the creature's power"));
    }

    private BorosFuryShield(final BorosFuryShield card) {
        super(card);
    }

    @Override
    public BorosFuryShield copy() {
        return new BorosFuryShield(this);
    }

    static class BorosFuryShieldDamageEffect extends OneShotEffect {

        BorosFuryShieldDamageEffect() {
            super(Outcome.Damage);
            staticText = "{this} deals damage to that creature's controller equal to the creature's power";
        }

        BorosFuryShieldDamageEffect(final BorosFuryShieldDamageEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent target = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            if (target != null) {
                Player player = game.getPlayer(target.getControllerId());
                if (player != null) {
                    int power = target.getPower().getValue();
                    player.damage(power, source.getId(), source, game);
                }

            }
            return false;
        }

        @Override
        public Effect copy() {
            return new BorosFuryShieldDamageEffect(this);
        }

    }
}
