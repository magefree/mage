
package mage.cards.w;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.RandomUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class WildSwing extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("target nonenchantment permanents");

    static {
        filter.add(Predicates.not(CardType.ENCHANTMENT.getPredicate()));
    }

    public WildSwing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");

        // Choose three target nonenchantment permanents. Destroy one of them at random.
        this.getSpellAbility().addEffect(new WildSwingEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(3, filter));

    }

    private WildSwing(final WildSwing card) {
        super(card);
    }

    @Override
    public WildSwing copy() {
        return new WildSwing(this);
    }
}

class WildSwingEffect extends OneShotEffect {

    public WildSwingEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Choose three target nonenchantment permanents. Destroy one of them at random";
    }

    private WildSwingEffect(final WildSwingEffect effect) {
        super(effect);
    }

    @Override
    public WildSwingEffect copy() {
        return new WildSwingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (!source.getTargets().isEmpty() && sourceObject != null) {
            Target target = source.getTargets().get(0);
            if (target != null && !target.getTargets().isEmpty()) {

                Permanent targetPermanent = game.getPermanent(target.getTargets().get(RandomUtil.nextInt(target.getTargets().size())));
                if (targetPermanent != null) {
                    game.informPlayers(sourceObject.getLogName() + ": The randomly chosen target to destroy is " + targetPermanent.getLogName());
                    targetPermanent.destroy(source, game, false);
                }
                return true;
            }
        }
        return false;
    }
}
