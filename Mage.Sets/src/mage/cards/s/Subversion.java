package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class Subversion extends CardImpl {

    public Subversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");


        // At the beginning of your upkeep, each opponent loses 1 life. You gain life equal to the life lost this way.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SubversionEffect(), TargetController.YOU, false));
    }

    private Subversion(final Subversion card) {
        super(card);
    }

    @Override
    public Subversion copy() {
        return new Subversion(this);
    }


    static class SubversionEffect extends OneShotEffect {

        public SubversionEffect() {
            super(Outcome.Damage);
            staticText = "each opponent loses 1 life. You gain life equal to the life lost this way";
        }

        private SubversionEffect(final SubversionEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            int damage = 0;
            for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                damage += game.getPlayer(opponentId).damage(1, source.getSourceId(), source, game);
            }
            game.getPlayer(source.getControllerId()).gainLife(damage, game, source);
            return true;
        }

        @Override
        public SubversionEffect copy() {
            return new SubversionEffect(this);
        }

    }
}
