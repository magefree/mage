package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @author LevelX2
 */
public final class SilenceTheBelievers extends CardImpl {

    public SilenceTheBelievers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Strive - Silence the Believers costs 2B more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{2}{B}"));

        // Exile any number of target creatures and all Auras attached to them.
        this.getSpellAbility().addEffect(new SilenceTheBelieversExileEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));

    }

    private SilenceTheBelievers(final SilenceTheBelievers card) {
        super(card);
    }

    @Override
    public SilenceTheBelievers copy() {
        return new SilenceTheBelievers(this);
    }
}

class SilenceTheBelieversExileEffect extends OneShotEffect {

    public SilenceTheBelieversExileEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile any number of target creatures and all Auras attached to them";
    }

    private SilenceTheBelieversExileEffect(final SilenceTheBelieversExileEffect effect) {
        super(effect);
    }

    @Override
    public SilenceTheBelieversExileEffect copy() {
        return new SilenceTheBelieversExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                Permanent creature = game.getPermanent(targetId);
                if (creature != null) {
                    List<UUID> attachments = new ArrayList<>(creature.getAttachments());
                    for (UUID attachmentId : attachments) {
                        Permanent attachment = game.getPermanent(attachmentId);
                        if (attachment != null && attachment.hasSubtype(SubType.AURA, game)) {
                            controller.moveCardToExileWithInfo(attachment, null, null, source, game, Zone.BATTLEFIELD, true);
                        }
                    }
                    controller.moveCardToExileWithInfo(creature, null, null, source, game, Zone.BATTLEFIELD, true);
                }
            }
            return true;
        }
        return false;
    }
}
