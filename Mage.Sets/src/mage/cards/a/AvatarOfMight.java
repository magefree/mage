
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class AvatarOfMight extends CardImpl {

    public AvatarOfMight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{G}{G}");
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // If an opponent controls at least four more creatures than you, Avatar of Might costs {6} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new AvatarOfMightCostReductionEffect()));

        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    public AvatarOfMight(final AvatarOfMight card) {
        super(card);
    }

    @Override
    public AvatarOfMight copy() {
        return new AvatarOfMight(this);
    }
}

class AvatarOfMightCostReductionEffect extends CostModificationEffectImpl {

    AvatarOfMightCostReductionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "If an opponent controls at least four more creatures than you, {this} costs {6} less to cast";
    }

    AvatarOfMightCostReductionEffect(final AvatarOfMightCostReductionEffect effect) {
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
        if (abilityToModify.getSourceId().equals(source.getSourceId()) && (abilityToModify instanceof SpellAbility)) {
            int creatures = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
            for (UUID playerId : game.getOpponents(source.getControllerId())) {
                Player opponent = game.getPlayer(playerId);
                if (opponent != null && game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, opponent.getId(), game) >= creatures + 4) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public AvatarOfMightCostReductionEffect copy() {
        return new AvatarOfMightCostReductionEffect(this);
    }
}
