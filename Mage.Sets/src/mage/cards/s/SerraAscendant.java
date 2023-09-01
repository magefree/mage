
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SerraAscendant extends CardImpl {

    public SerraAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Lifelink (Damage dealt by this creature also causes you to gain that much life.)
        this.addAbility(LifelinkAbility.getInstance());

        // As long as you have 30 or more life, Serra Ascendant gets +5/+5 and has flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SerraAscendantEffect()));
    }

    private SerraAscendant(final SerraAscendant card) {
        super(card);
    }

    @Override
    public SerraAscendant copy() {
        return new SerraAscendant(this);
    }

}

class SerraAscendantEffect extends ContinuousEffectImpl {

    public SerraAscendantEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BoostCreature);
        staticText = "As long as you have 30 or more life, {this} gets +5/+5 and has flying";
    }

    private SerraAscendantEffect(final SerraAscendantEffect effect) {
        super(effect);
    }

    @Override
    public SerraAscendantEffect copy() {
        return new SerraAscendantEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature = game.getPermanent(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (creature != null && controller != null) {
            if (controller.getLife() >= 30) {
                switch (layer) {
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.ModifyPT_7c) {
                            creature.addPower(5);
                            creature.addToughness(5);
                        }
                        break;
                    case AbilityAddingRemovingEffects_6:
                        if (sublayer == SubLayer.NA) {
                            creature.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                        }
                        break;
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
        return Layer.AbilityAddingRemovingEffects_6 == layer || Layer.PTChangingEffects_7 == layer;
    }

}
