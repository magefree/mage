package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.FoodToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OkoThiefOfCrowns extends CardImpl {

    private static final FilterPermanent filter
            = new FilterOpponentsCreaturePermanent("creature an opponent controls with power 3 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 4));
    }

    public OkoThiefOfCrowns(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.OKO);
        this.setStartingLoyalty(4);

        // +2: Create a Food token.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new FoodToken()), 2));

        // +1: Target artifact or creature loses all abilities and becomes a green Elk creature with base power and toughness 3/3.
        Ability ability = new LoyaltyAbility(new OkoThiefOfCrownsEffect(), 1);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_CREATURE));
        this.addAbility(ability);

        // −5: Exchange control of target artifact or creature you control and target creature an opponent controls with power 3 or less.
        ability = new LoyaltyAbility(new ExchangeControlTargetEffect(
                Duration.EndOfGame, "exchange control of target artifact or creature you control " +
                "and target creature an opponent controls with power 3 or less", false, true
        ), -5);
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT_OR_CREATURE));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private OkoThiefOfCrowns(final OkoThiefOfCrowns card) {
        super(card);
    }

    @Override
    public OkoThiefOfCrowns copy() {
        return new OkoThiefOfCrowns(this);
    }
}

class OkoThiefOfCrownsEffect extends ContinuousEffectImpl {

    OkoThiefOfCrownsEffect() {
        super(Duration.Custom, Outcome.Benefit);
        staticText = "target artifact or creature loses all abilities " +
                "and becomes a green Elk creature with base power and toughness 3/3";
    }

    private OkoThiefOfCrownsEffect(final OkoThiefOfCrownsEffect effect) {
        super(effect);
    }

    @Override
    public OkoThiefOfCrownsEffect copy() {
        return new OkoThiefOfCrownsEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllCardTypes(game);
                permanent.removeAllSubTypes(game);
                permanent.addCardType(game, CardType.CREATURE);
                permanent.addSubType(game, SubType.ELK);
                return true;
            case ColorChangingEffects_5:
                permanent.getColor(game).setWhite(false);
                permanent.getColor(game).setBlue(false);
                permanent.getColor(game).setBlack(false);
                permanent.getColor(game).setRed(false);
                permanent.getColor(game).setGreen(true);
                return true;
            case AbilityAddingRemovingEffects_6:
                permanent.removeAllAbilities(source.getSourceId(), game);
                return true;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    permanent.getPower().setModifiedBaseValue(3);
                    permanent.getToughness().setModifiedBaseValue(3);
                    return true;
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
        switch (layer) {
            case TypeChangingEffects_4:
            case ColorChangingEffects_5:
            case AbilityAddingRemovingEffects_6:
            case PTChangingEffects_7:
                return true;
        }
        return false;
    }
}
