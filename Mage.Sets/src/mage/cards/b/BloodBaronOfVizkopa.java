
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

public final class BloodBaronOfVizkopa extends CardImpl {

    public BloodBaronOfVizkopa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{B}");
        this.subtype.add(SubType.VAMPIRE);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink, protection from white and from black.
        this.addAbility(LifelinkAbility.getInstance());
        this.addAbility(ProtectionAbility.from(ObjectColor.WHITE, ObjectColor.BLACK));

        // As long as you have 30 or more life and an opponent has 10 or less life, Blood Baron of Vizkopa gets +6/+6 and has flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BloodBaronOfVizkopaEffect()));

    }

    private BloodBaronOfVizkopa(final BloodBaronOfVizkopa card) {
        super(card);
    }

    @Override
    public BloodBaronOfVizkopa copy() {
        return new BloodBaronOfVizkopa(this);
    }

}

class BloodBaronOfVizkopaEffect extends ContinuousEffectImpl {

    public BloodBaronOfVizkopaEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "As long as you have 30 or more life and an opponent has 10 or less life, {this} gets +6/+6 and has flying";
    }

    public BloodBaronOfVizkopaEffect(final BloodBaronOfVizkopaEffect effect) {
        super(effect);
    }

    @Override
    public BloodBaronOfVizkopaEffect copy() {
        return new BloodBaronOfVizkopaEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (conditionState(source, game)) {
            Permanent creature = game.getPermanent(source.getSourceId());
            if (creature != null) {
                switch (layer) {
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.ModifyPT_7c) {
                            creature.addPower(6);
                            creature.addToughness(6);
                        }
                        break;
                    case AbilityAddingRemovingEffects_6:
                        if (sublayer == SubLayer.NA) {
                            creature.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                        }
                        break;
                    default:
                }
                return true;
            }
        }
        return false;
    }

    protected boolean conditionState(Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && controller.getLife() >= 30) {
            for (UUID opponentId : game.getState().getPlayersInRange(controller.getId(), game)) {
                if (controller.hasOpponent(opponentId, game)) {
                    Player opponent = game.getPlayer(opponentId);
                    if (opponent != null && opponent.getLife() < 11) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return (layer == Layer.AbilityAddingRemovingEffects_6 || layer == layer.PTChangingEffects_7);

    }

}
