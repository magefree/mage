package mage.cards.h;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.awt.*;
import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class HydroManFluidFelon extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a blue spell");

    private static final Condition condition = new SourceMatchesFilterCondition(
            "this {this} is a creature", StaticFilters.FILTER_PERMANENT_CREATURE
    );

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public HydroManFluidFelon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a blue spell, if Hydro-Man is a creature, he gets +1/+1 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn), filter,false)
                .withInterveningIf(condition).withRuleTextReplacement(true));

        // At the beginning of your end step, untap Hydro-Man. Until your next turn, he becomes a land and gains "{T}: Add {U}."
        Ability ability = new BeginningOfEndStepTriggeredAbility(new UntapSourceEffect());
        ability.addEffect(new HydroManFluidFelonEffect());
        this.addAbility(ability);
    }

    private HydroManFluidFelon(final HydroManFluidFelon card) {
        super(card);
    }

    @Override
    public HydroManFluidFelon copy() {
        return new HydroManFluidFelon(this);
    }
}
class HydroManFluidFelonEffect extends ContinuousEffectImpl {

    HydroManFluidFelonEffect() {
        super(Duration.UntilYourNextTurn, Outcome.Neutral);
        this.staticText = "Until your next turn, he becomes a land and gains \"{T}: Add {U}.\"";
        this.addDependencyType(DependencyType.BecomeNonbasicLand);
    }

    protected HydroManFluidFelonEffect(final HydroManFluidFelonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.removeAllCardTypes(game);
                permanent.removeAllCreatureTypes(game);
                permanent.addCardType(game, CardType.LAND);
                break;
            case AbilityAddingRemovingEffects_6:
                permanent.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                break;
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4 || layer == Layer.AbilityAddingRemovingEffects_6;
    }

    @Override
    public HydroManFluidFelonEffect copy() {
        return new HydroManFluidFelonEffect(this);
    }
}
