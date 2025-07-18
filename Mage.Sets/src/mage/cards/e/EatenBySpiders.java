
package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.LinkedList;
import java.util.UUID;

/**
 * @author North
 */
public final class EatenBySpiders extends CardImpl {

    public EatenBySpiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Destroy target creature with flying and all Equipment attached to that creature.
        this.getSpellAbility().addEffect(new EatenBySpidersEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_CREATURE_FLYING));
    }

    private EatenBySpiders(final EatenBySpiders card) {
        super(card);
    }

    @Override
    public EatenBySpiders copy() {
        return new EatenBySpiders(this);
    }
}

class EatenBySpidersEffect extends OneShotEffect {

    EatenBySpidersEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy target creature with flying and all Equipment attached to that creature";
    }

    private EatenBySpidersEffect(final EatenBySpidersEffect effect) {
        super(effect);
    }

    @Override
    public EatenBySpidersEffect copy() {
        return new EatenBySpidersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            LinkedList<UUID> attachments = new LinkedList<>();
            attachments.addAll(permanent.getAttachments());

            for (UUID attachmentId : attachments) {
                Permanent attachment = game.getPermanent(attachmentId);
                if (attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                    attachment.destroy(source, game, false);
                }
            }

            permanent.destroy(source, game, false);
            return true;
        }
        return false;
    }
}
