
package mage.cards.t;

import java.util.LinkedList;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public final class TreefolkMystic extends CardImpl {

    public TreefolkMystic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Treefolk Mystic blocks or becomes blocked by a creature, destroy all Auras attached to that creature.
        this.addAbility(new BlocksOrBecomesBlockedSourceTriggeredAbility(new TreefolkMysticEffect(), false));
    }

    private TreefolkMystic(final TreefolkMystic card) {
        super(card);
    }

    @Override
    public TreefolkMystic copy() {
        return new TreefolkMystic(this);
    }
}

class TreefolkMysticEffect extends OneShotEffect {

    public TreefolkMysticEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "destroy all Auras attached to that creature";
    }

    public TreefolkMysticEffect(final TreefolkMysticEffect effect) {
        super(effect);
    }

    @Override
    public TreefolkMysticEffect copy() {
        return new TreefolkMysticEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            LinkedList<UUID> attachments = new LinkedList();
            attachments.addAll(permanent.getAttachments());
            for (UUID uuid : attachments) {
                Permanent aura = game.getPermanent(uuid);
                if (aura != null && aura.hasSubtype(SubType.AURA, game)) {
                    aura.destroy(source, game, false);
                }
            }
        }
        return false;
    }
}
