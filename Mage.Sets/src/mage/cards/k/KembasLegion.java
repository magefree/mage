
package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public final class KembasLegion extends CardImpl {

    public KembasLegion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}");
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        this.addAbility(VigilanceAbility.getInstance());

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KembasLegionEffect()));
    }

    private KembasLegion(final KembasLegion card) {
        super(card);
    }

    @Override
    public KembasLegion copy() {
        return new KembasLegion(this);
    }
}

class KembasLegionEffect extends ContinuousEffectImpl {

    public KembasLegionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "{this} can block an additional creature for each Equipment attached to Kemba's Legion";
    }

    private KembasLegionEffect(final KembasLegionEffect effect) {
        super(effect);
    }

    @Override
    public KembasLegionEffect copy() {
        return new KembasLegionEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && !permanent.getAttachments().isEmpty()) {
            if (layer == Layer.RulesEffects) {
                // maxBlocks = 0 equals to "can block any number of creatures"
                if (permanent.getMaxBlocks() > 0) {
                    List<UUID> attachments = permanent.getAttachments();
                    int count = 0;
                    for (UUID attachmentId : attachments) {
                        Permanent attachment = game.getPermanent(attachmentId);
                        if (attachment != null && attachment.hasSubtype(SubType.EQUIPMENT, game)) {
                            count++;
                        }
                    }
                    permanent.setMaxBlocks(permanent.getMaxBlocks() + count);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
