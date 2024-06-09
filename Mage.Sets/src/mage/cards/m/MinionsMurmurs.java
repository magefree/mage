package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Derpthemeus
 */
public final class MinionsMurmurs extends CardImpl {

    public MinionsMurmurs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // You draw X cards and you lose X life, where X is the number of creatures you control.
        this.getSpellAbility().addEffect(new MinionsMurmursEffect());
        this.getSpellAbility().addHint(CreaturesYouControlHint.instance);
    }

    private MinionsMurmurs(final MinionsMurmurs card) {
        super(card);
    }

    @Override
    public MinionsMurmurs copy() {
        return new MinionsMurmurs(this);
    }

    static class MinionsMurmursEffect extends OneShotEffect {

        public MinionsMurmursEffect() {
            super(Outcome.DrawCard);
            this.staticText = "You draw X cards and you lose X life, where X is the number of creatures you control";
        }

        private MinionsMurmursEffect(final MinionsMurmursEffect effect) {
            super(effect);
        }

        @Override
        public MinionsMurmursEffect copy() {
            return new MinionsMurmursEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                int creaturesControlled = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, controller.getId(), game);
                controller.drawCards(creaturesControlled, source, game);
                controller.loseLife(creaturesControlled, game, source, false);
                return true;
            }
            return false;
        }
    }
}
