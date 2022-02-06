package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class MaskOfGriselbrand extends CardImpl {

    public MaskOfGriselbrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has flying and lifelink.
        Ability ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.EQUIPMENT));
        ability.addEffect(new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and lifelink"));
        this.addAbility(ability);

        // Whenever equipped creature dies, you may pay X life, where X is its power. If you do, draw X cards.
        this.addAbility(new DiesAttachedTriggeredAbility(new MaskOfGriselbrandEffect(), "equipped creature"));

        // Equip {3}
        this.addAbility(new EquipAbility(3, false));
    }

    private MaskOfGriselbrand(final MaskOfGriselbrand card) {
        super(card);
    }

    @Override
    public MaskOfGriselbrand copy() {
        return new MaskOfGriselbrand(this);
    }
}

class MaskOfGriselbrandEffect extends OneShotEffect {

    public MaskOfGriselbrandEffect() {
        super(Outcome.DrawCard);
        staticText = "you may pay X life, where X is its power. If you do, draw X cards";
    }

    private MaskOfGriselbrandEffect(final MaskOfGriselbrandEffect effect) {
        super(effect);
    }

    @Override
    public MaskOfGriselbrandEffect copy() {
        return new MaskOfGriselbrandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) getValue("attachedTo");
        if (controller == null || permanent == null) {
            return false;
        }
        int xValue = permanent.getPower().getValue();
        Cost cost = new PayLifeCost(xValue);
        if (cost.canPay(source, source, source.getControllerId(), game)
                && controller.chooseUse(
                outcome, "Pay " + xValue + " life? If you do, draw " +
                        xValue + " cards.", source, game
        ) && cost.pay(source, game, source, source.getControllerId(), false)) {
            controller.drawCards(xValue, source, game);
        }
        return true;
    }
}
