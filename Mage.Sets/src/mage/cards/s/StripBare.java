
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class StripBare extends CardImpl {

    public StripBare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Destroy all Auras and Equipment attached to target creature.
        this.getSpellAbility().addEffect(new StripBareEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private StripBare(final StripBare card) {
        super(card);
    }

    @Override
    public StripBare copy() {
        return new StripBare(this);
    }
}

class StripBareEffect extends OneShotEffect {

    public StripBareEffect() {
        super(Outcome.GainLife);
        this.staticText = "Destroy all Auras and Equipment attached to target creature";
    }

    public StripBareEffect(final StripBareEffect effect) {
        super(effect);
    }

    @Override
    public StripBareEffect copy() {
        return new StripBareEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean applied = false;
        FilterPermanent filter = new FilterPermanent();
        filter.add(Predicates.or(SubType.EQUIPMENT.getPredicate(),
                SubType.AURA.getPredicate()));
        Permanent targetCreature = game.getPermanent(source.getFirstTarget());
        if (targetCreature != null
                && !targetCreature.getAttachments().isEmpty()) {
            for (Permanent attachment : game.getBattlefield().getAllActivePermanents(filter, game)) {
                if (attachment != null
                        && targetCreature.getAttachments().contains(attachment.getId())) {
                    applied = attachment.destroy(source, game, false);
                }
            }
        }
        return applied;
    }
}
