package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EidolonOfObstruction extends CardImpl {

    public EidolonOfObstruction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Loyalty abilities of planeswalkers your opponents control cost {1} more to activate.
        this.addAbility(new SimpleStaticAbility(new EidolonOfObstructionEffect()));
    }

    private EidolonOfObstruction(final EidolonOfObstruction card) {
        super(card);
    }

    @Override
    public EidolonOfObstruction copy() {
        return new EidolonOfObstruction(this);
    }
}

class EidolonOfObstructionEffect extends CostModificationEffectImpl {

    EidolonOfObstructionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Loyalty abilities of planeswalkers your opponents control cost {1} more to activate";
    }

    private EidolonOfObstructionEffect(EidolonOfObstructionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof LoyaltyAbility)) {
            return false;
        }
        Permanent permanent = game.getPermanent(abilityToModify.getSourceId());
        if (permanent == null) {
            return false;
        }
        return permanent.isPlaneswalker(game)
                && game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId());
    }

    @Override
    public EidolonOfObstructionEffect copy() {
        return new EidolonOfObstructionEffect(this);
    }
}