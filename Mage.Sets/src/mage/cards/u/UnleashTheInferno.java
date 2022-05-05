package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnleashTheInferno extends CardImpl {

    public UnleashTheInferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}{R}{G}");

        // Unleash the Inferno deals 7 damage to target creature or planeswalker. When it deals excess damage this way destroy target artifact or enchantment an opponent controls with mana value less than or equal to that amount of excess damage.
        this.getSpellAbility().addEffect(new UnleashTheInfernoEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private UnleashTheInferno(final UnleashTheInferno card) {
        super(card);
    }

    @Override
    public UnleashTheInferno copy() {
        return new UnleashTheInferno(this);
    }
}

class UnleashTheInfernoEffect extends OneShotEffect {

    UnleashTheInfernoEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 7 damage to target creature or planeswalker. " +
                "When it deals excess damage this way, destroy target artifact or enchantment " +
                "an opponent controls with mana value less than or equal to that amount of excess damage";
    }

    private UnleashTheInfernoEffect(final UnleashTheInfernoEffect effect) {
        super(effect);
    }

    @Override
    public UnleashTheInfernoEffect copy() {
        return new UnleashTheInfernoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        int lethal = Math.min(permanent.getLethalDamage(source.getSourceId(), game), 7);
        permanent.damage(7, source, game);
        int excess = 7 - lethal;
        if (lethal > 0) {
            return true;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        FilterPermanent filter = new FilterArtifactOrEnchantmentPermanent(
                "artifact or enchantment an opponent controls with mana value less that or equal to " + excess
        );
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, excess));
        ability.addTarget(new TargetPermanent(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
