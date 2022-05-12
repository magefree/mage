
package mage.cards.g;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class GriftersBlade extends CardImpl {

    public GriftersBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        this.subtype.add(SubType.EQUIPMENT);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // As Grifter's Blade enters the battlefield, choose a creature you control it could be attached to. If you do, it enters the battlefield attached to that creature.
        this.addAbility(new AsEntersBattlefieldAbility(new GriftersBladeChooseCreatureEffect(Outcome.BoostCreature)));

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(1, 1)));

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));

    }

    private GriftersBlade(final GriftersBlade card) {
        super(card);
    }

    @Override
    public GriftersBlade copy() {
        return new GriftersBlade(this);
    }
}

class GriftersBladeChooseCreatureEffect extends OneShotEffect {

    public GriftersBladeChooseCreatureEffect(Outcome outcome) {
        super(outcome);
        this.staticText = "choose a creature you control it could be attached to. If you do, it enters the battlefield attached to that creature";
    }

    public GriftersBladeChooseCreatureEffect(final GriftersBladeChooseCreatureEffect effect) {
        super(effect);
    }

    @Override
    public GriftersBladeChooseCreatureEffect copy() {
        return new GriftersBladeChooseCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getPermanentEntering(source.getSourceId());
        if (controller != null && mageObject != null) {
            TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
            target.setNotTarget(true);
            if (controller.choose(this.outcome, target, source, game)) {
                Permanent attachToCreature = game.getPermanent(target.getFirstTarget());
                if (attachToCreature != null) {
                    attachToCreature.addAttachment(mageObject.getId(), source, game);
                }
            }
            return true;
        }
        return false;
    }
}
