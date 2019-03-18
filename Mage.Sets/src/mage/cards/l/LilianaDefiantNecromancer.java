package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlaneswalkerEntersWithLoyaltyCountersAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.command.emblems.LilianaDefiantNecromancerEmblem;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

/**
 *
 * @author LevelX2
 */
public final class LilianaDefiantNecromancer extends CardImpl {

    protected static final FilterCreatureCard filter = new FilterCreatureCard("nonlegendary creature with converted mana cost X from your graveyard");

    static {
        filter.add(Predicates.not(new SupertypePredicate(SuperType.LEGENDARY)));
    }

    public LilianaDefiantNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);
        this.color.setBlack(true);

        this.nightCard = true;

        this.addAbility(new PlaneswalkerEntersWithLoyaltyCountersAbility(3));

        // +2: Each player discards a card.
        this.addAbility(new LoyaltyAbility(new DiscardEachPlayerEffect(1, false), 2));

        // -X: Return target nonlegendary creature with converted mana cost X from your graveyard to the battlefield.
        Ability ability = new LoyaltyAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.setTargetAdjuster(LilianaDefiantNecromancerAdjuster.instance);
        this.addAbility(ability);

        //-8: You get an emblem with "Whenever a creature dies, return it to the battlefield under your control at the beginning of the next end step.";
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new LilianaDefiantNecromancerEmblem()), -8));
    }

    public LilianaDefiantNecromancer(final LilianaDefiantNecromancer card) {
        super(card);
    }

    @Override
    public LilianaDefiantNecromancer copy() {
        return new LilianaDefiantNecromancer(this);
    }
}

enum LilianaDefiantNecromancerAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int cmc = 0;
        for (Cost cost : ability.getCosts()) {
            if (cost instanceof PayVariableLoyaltyCost) {
                cmc = ((PayVariableLoyaltyCost) cost).getAmount();
            }
        }
        FilterCard newFilter = LilianaDefiantNecromancer.filter.copy();
        newFilter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, cmc));
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(newFilter));
    }
}
