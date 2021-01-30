package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ForetellAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
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
        this.addAbility(new SimpleStaticAbility(new CosmosChargerCostReductionEffect(ForetellAbility.class)));
        this.addAbility(new SimpleStaticAbility(new CosmosChargerAllowForetellAnytime()));

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

    private final Class specialAction;

    public CosmosChargerCostReductionEffect(Class specialAction) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, CostModificationType.REDUCE_COST);
        this.specialAction = specialAction;
        staticText = "Foretelling cards from your hand costs {1} less and can be done on any player's turn";
    }

    public CosmosChargerCostReductionEffect(CosmosChargerCostReductionEffect effect) {
        super(effect);
        this.specialAction = effect.specialAction;
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
                && specialAction.isInstance(abilityToModify);
    }
}

class CosmosChargerAllowForetellAnytime extends AsThoughEffectImpl {

    public CosmosChargerAllowForetellAnytime() {
        super(AsThoughEffectType.ALLOW_FORETELL_ANYTIME, Duration.WhileOnBattlefield, Outcome.Benefit);
    }

    public CosmosChargerAllowForetellAnytime(final CosmosChargerAllowForetellAnytime effect) {
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
