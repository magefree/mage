
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class AvatarOfWill extends CardImpl {

    public AvatarOfWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{U}{U}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // If an opponent has no cards in hand, Avatar of Will costs {6} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new AvatarOfWillCostReductionEffect()));

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    public AvatarOfWill(final AvatarOfWill card) {
        super(card);
    }

    @Override
    public AvatarOfWill copy() {
        return new AvatarOfWill(this);
    }
}

class AvatarOfWillCostReductionEffect extends CostModificationEffectImpl {

    AvatarOfWillCostReductionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "If an opponent has no cards in hand, {this} costs {6} less to cast";
    }

    AvatarOfWillCostReductionEffect(final AvatarOfWillCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        Mana mana = spellAbility.getManaCostsToPay().getMana();
        if (mana.getGeneric() > 0) {
            int newCount = mana.getGeneric() - 6;
            if (newCount < 0) {
                newCount = 0;
            }
            mana.setGeneric(newCount);
            spellAbility.getManaCostsToPay().load(mana.toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getSourceId().equals(source.getSourceId())) {
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null && opponent.getHand().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public AvatarOfWillCostReductionEffect copy() {
        return new AvatarOfWillCostReductionEffect(this);
    }
}
