
package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class TerashisGrasp extends CardImpl {

    public TerashisGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}");
        this.subtype.add(SubType.ARCANE);

        // Destroy target artifact or enchantment. 
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        // You gain life equal to its converted mana cost.
        this.getSpellAbility().addEffect(new TerashisGraspEffect());
    }

    private TerashisGrasp(final TerashisGrasp card) {
        super(card);
    }

    @Override
    public TerashisGrasp copy() {
        return new TerashisGrasp(this);
    }

    private static class TerashisGraspEffect extends OneShotEffect {

        public TerashisGraspEffect() {
            super(Outcome.DestroyPermanent);
            staticText = "You gain life equal to its mana value";
        }

        private TerashisGraspEffect(final TerashisGraspEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent targetPermanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            if (targetPermanent != null) {
                int cost = targetPermanent.getManaValue();
                Player player = game.getPlayer(source.getControllerId());
                if (player != null) {
                    player.gainLife(cost, game, source);
                }
            }
            return true;
        }

        @Override
        public TerashisGraspEffect copy() {
            return new TerashisGraspEffect(this);
        }
    }
}
