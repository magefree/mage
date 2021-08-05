package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class CosmosCharger extends CardImpl {

    public CosmosCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HORSE);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Foretelling cards from your hand costs {1} less and can be done on any player's turn. 
        Ability ability = new SimpleStaticAbility(new CosmosChargerCostReductionEffect());
        ability.addEffect(new CosmosChargerAllowForetellAnytime());
        this.addAbility(ability);

        // Foretell 2U
        this.addAbility(new ForetellAbility(this, "{2}{U}"));
    }

    private CosmosCharger(final CosmosCharger card) {
        super(card);
    }

    @Override
    public CosmosCharger copy() {
        return new CosmosCharger(this);
    }
}

class CosmosChargerCostReductionEffect extends CostModificationEffectImpl {

    CosmosChargerCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, CostModificationType.REDUCE_COST);
        staticText = "foretelling cards from your hand costs {1} less";
    }

    private CosmosChargerCostReductionEffect(CosmosChargerCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public CosmosChargerCostReductionEffect copy() {
        return new CosmosChargerCostReductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isControlledBy(source.getControllerId())
                && abilityToModify instanceof ForetellAbility;
    }
}

class CosmosChargerAllowForetellAnytime extends AsThoughEffectImpl {

    CosmosChargerAllowForetellAnytime() {
        super(AsThoughEffectType.ALLOW_FORETELL_ANYTIME, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "and can be done on any player's turn";
    }

    private CosmosChargerAllowForetellAnytime(final CosmosChargerAllowForetellAnytime effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public CosmosChargerAllowForetellAnytime copy() {
        return new CosmosChargerAllowForetellAnytime(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return true;
    }
}
