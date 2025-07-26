
package mage.cards.a;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author North
 */
public final class ArmamentMaster extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Other Kor creatures you control");

    static {
        filter.add(SubType.KOR.getPredicate());
    }

    public ArmamentMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new SimpleStaticAbility(new ArmamentMasterEffect()));
    }

    private ArmamentMaster(final ArmamentMaster card) {
        super(card);
    }

    @Override
    public ArmamentMaster copy() {
        return new ArmamentMaster(this);
    }
}

class ArmamentMasterEffect extends ContinuousEffectImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Other Kor creatures you control");

    static {
        filter.add(SubType.KOR.getPredicate());
    }

    public ArmamentMasterEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Other Kor creatures you control get +2/+2 for each Equipment attached to {this}";
    }

    private ArmamentMasterEffect(final ArmamentMasterEffect effect) {
        super(effect);
    }

    @Override
    public ArmamentMasterEffect copy() {
        return new ArmamentMasterEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        int equipmentCount = countEquipment(game, source);
        for (MageItem object : affectedObjects) {
            Permanent permanent = (Permanent) object;
            permanent.addPower(2 * equipmentCount);
            permanent.addToughness(2 * equipmentCount);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        int equipmentCount = countEquipment(game, source);
        if (equipmentCount < 1) {
            return false;
        }
        affectedObjects.addAll(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game));
        return !affectedObjects.isEmpty();
    }

    private int countEquipment(Game game, Ability source) {
        int count = 0;
        Permanent p = game.getPermanent(source.getSourceId());
        if (p != null) {
            List<UUID> attachments = p.getAttachments();
            for (UUID attachmentId : attachments) {
                Permanent attached = game.getPermanent(attachmentId);
                if (attached != null && attached.hasSubtype(SubType.EQUIPMENT, game)) {
                    count++;
                }
            }

        }
        return count;
    }

}
