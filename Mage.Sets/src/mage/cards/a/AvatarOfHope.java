package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author Plopman
 */
public final class AvatarOfHope extends CardImpl {

    public AvatarOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{W}{W}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(9);

        // If you have 3 or less life, Avatar of Hope costs {6} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AvatarOfHopeAdjustingCostsEffect()));
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Avatar of Hope can block any number of creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(0)));
    }

    public AvatarOfHope(final AvatarOfHope card) {
        super(card);
    }

    @Override
    public AvatarOfHope copy() {
        return new AvatarOfHope(this);
    }
}

class AvatarOfHopeAdjustingCostsEffect extends CostModificationEffectImpl {

    AvatarOfHopeAdjustingCostsEffect() {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "If you have 3 or less life, {this} costs {6} less to cast";
    }

    AvatarOfHopeAdjustingCostsEffect(AvatarOfHopeAdjustingCostsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 6);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getSourceId().equals(source.getSourceId())
                && (abilityToModify instanceof SpellAbility)) {
            Player player = game.getPlayer(abilityToModify.getControllerId());
            if (player != null && player.getLife() < 4) {
                return true;
            }
        }

        return false;
    }

    @Override
    public AvatarOfHopeAdjustingCostsEffect copy() {
        return new AvatarOfHopeAdjustingCostsEffect(this);
    }

}
