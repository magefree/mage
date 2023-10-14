
package mage.cards.s;

import java.util.LinkedList;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author TheElk801
 */
public final class SoulNova extends CardImpl {

    public SoulNova(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}{W}");

        // Exile target attacking creature and all Equipment attached to it.
        this.getSpellAbility().addEffect(new SoulNovaEffect());
        this.getSpellAbility().addTarget(new TargetAttackingCreature());
    }

    private SoulNova(final SoulNova card) {
        super(card);
    }

    @Override
    public SoulNova copy() {
        return new SoulNova(this);
    }
}

class SoulNovaEffect extends OneShotEffect {

    public SoulNovaEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Exile target attacking creature and all Equipment attached to it.";
    }

    private SoulNovaEffect(final SoulNovaEffect effect) {
        super(effect);
    }

    @Override
    public SoulNovaEffect copy() {
        return new SoulNovaEffect(this);
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
                    attachment.moveToExile(null, "", source, game);
                }
            }

            permanent.moveToExile(null, "", source, game);
            return true;
        }
        return false;
    }
}
