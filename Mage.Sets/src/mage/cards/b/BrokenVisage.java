
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.BrokenVisageSpiritToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class BrokenVisage extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact attacking creature");
    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(AttackingPredicate.instance);
    }

    public BrokenVisage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // Destroy target nonartifact attacking creature. It can't be regenerated. Create a black Spirit creature token. Its power is equal to that creature's power and its toughness is equal to that creature's toughness. Sacrifice the token at the beginning of the next end step.
        this.getSpellAbility().addEffect(new BrokenVisageEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private BrokenVisage(final BrokenVisage card) {
        super(card);
    }

    @Override
    public BrokenVisage copy() {
        return new BrokenVisage(this);
    }
}

class BrokenVisageEffect extends OneShotEffect {

    public BrokenVisageEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target nonartifact attacking creature. It can't be regenerated. Create a black Spirit creature token. Its power is equal to that creature's power and its toughness is equal to that creature's toughness. Sacrifice the token at the beginning of the next end step";
    }

    private BrokenVisageEffect(final BrokenVisageEffect effect) {
        super(effect);
    }

    @Override
    public BrokenVisageEffect copy() {
        return new BrokenVisageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (permanent != null) { 
            permanent.destroy(source, game, true);
            CreateTokenEffect effect = new CreateTokenEffect(new BrokenVisageSpiritToken(permanent.getPower().getValue(), permanent.getToughness().getValue()));
            effect.apply(game, source);
            for (UUID tokenId : effect.getLastAddedTokenIds()) {
                Permanent tokenPermanent = game.getPermanent(tokenId);
                if (tokenPermanent != null) {
                    SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("Sacrifice the token at the beginning of the next end step", source.getControllerId());
                    sacrificeEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(sacrificeEffect);
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
            }
        }
        return true;
    }

}
