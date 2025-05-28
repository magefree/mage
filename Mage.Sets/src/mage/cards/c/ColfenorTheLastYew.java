package mage.cards.c;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.GenericTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ColfenorTheLastYew extends CardImpl {

    public ColfenorTheLastYew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(7);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Colfenor, the Last Yew or another creature you control dies, return up to one other target creature card with lesser toughness from your graveyard to your hand.
        Ability ability = new DiesThisOrAnotherTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), false, StaticFilters.FILTER_CONTROLLED_CREATURE);
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, new FilterCreatureCard("other target creature card with lesser toughness")));
        ability.setTargetAdjuster(new ColfenorTheLastYewTargetAdjuster());
        this.addAbility(ability);
    }

    private ColfenorTheLastYew(final ColfenorTheLastYew card) {
        super(card);
    }

    @Override
    public ColfenorTheLastYew copy() {
        return new ColfenorTheLastYew(this);
    }
}
class ColfenorTheLastYewTargetAdjuster extends GenericTargetAdjuster {
    @Override
    public void adjustTargets(Ability ability, Game game) {
        Permanent permanent = (Permanent)ability.getEffects().get(0).getValue("creatureDied");
        ability.getTargets().clear();
        Target newTarget = blueprintTarget.copy();
        FilterCard filterCard = new FilterCreatureCard("creature card with toughness less than " + permanent.getToughness().getValue());
        filterCard.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, permanent.getToughness().getValue()));
        filterCard.add(Predicates.not(new MageObjectReferencePredicate(new MageObjectReference(permanent, game))));
        ability.addTarget(newTarget);
    }
}
