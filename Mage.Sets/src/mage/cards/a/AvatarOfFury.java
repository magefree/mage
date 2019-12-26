package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public final class AvatarOfFury extends CardImpl {

    public AvatarOfFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // If an opponent controls seven or more lands, Avatar of Fury costs {6} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AvatarOfFuryAdjustingCostsEffect()));
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {R}: Avatar of Fury gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{R}")));
    }

    public AvatarOfFury(final AvatarOfFury card) {
        super(card);
    }

    @Override
    public AvatarOfFury copy() {
        return new AvatarOfFury(this);
    }
}

class AvatarOfFuryAdjustingCostsEffect extends CostModificationEffectImpl {

    AvatarOfFuryAdjustingCostsEffect() {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "If an opponent controls seven or more lands, {this} costs {6} less to cast";
    }

    AvatarOfFuryAdjustingCostsEffect(AvatarOfFuryAdjustingCostsEffect effect) {
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
            for (UUID playerId : game.getOpponents(abilityToModify.getControllerId())) {
                if (game.getBattlefield().countAll(StaticFilters.FILTER_LAND, playerId, game) > 6) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public AvatarOfFuryAdjustingCostsEffect copy() {
        return new AvatarOfFuryAdjustingCostsEffect(this);
    }

}
