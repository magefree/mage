
package mage.cards.b;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author L_J
 */
public final class BakisCurse extends CardImpl {

    public BakisCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{U}{U}");

        // Baki's Curse deals 2 damage to each creature for each Aura attached to that creature.
        this.getSpellAbility().addEffect(new BakisCurseEffect());
    }

    private BakisCurse(final BakisCurse card) {
        super(card);
    }

    @Override
    public BakisCurse copy() {
        return new BakisCurse(this);
    }
}

class BakisCurseEffect extends OneShotEffect {
    
    public BakisCurseEffect() {
            super(Outcome.Detriment);
            staticText = "Baki's Curse deals 2 damage to each creature for each Aura attached to that creature.";
    }

    public BakisCurseEffect(final BakisCurseEffect effect) {
        super(effect);
    }

    @Override
    public BakisCurseEffect copy() {
        return new BakisCurseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(new FilterCreaturePermanent(), source.getControllerId(), source, game)) {
            int count = 0;
            List<UUID> attachments = creature.getAttachments();
            for (UUID attachmentId : attachments) {
                Permanent attached = game.getPermanent(attachmentId);
                if (attached != null && attached.hasSubtype(SubType.AURA, game)) {
                    count++;
                }
            }
            creature.damage(count * 2, source.getId(), source, game, false, true);
        }
        return true;
    }
}
