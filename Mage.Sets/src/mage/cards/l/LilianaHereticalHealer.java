package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.Pronoun;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayVariableLoyaltyCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.command.emblems.LilianaDefiantNecromancerEmblem;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LilianaHereticalHealer extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another nontoken creature you control");
    static final FilterCreatureCard filter2 = new FilterCreatureCard("nonlegendary creature card with mana value X from your graveyard");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
        filter2.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public LilianaHereticalHealer(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{1}{B}{B}",
                "Liliana, Defiant Necromancer",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.LILIANA}, "B"
        );
        this.getLeftHalfCard().setPT(2, 3);
        this.getRightHalfCard().setStartingLoyalty(3);

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // Whenever another nontoken creature you control dies, exile Liliana Heretical Healer, then return her to the battlefield transformed under her owner's control. If you do, create a 2/2 black Zombie creature token.
        this.getLeftHalfCard().addAbility(new DiesCreatureTriggeredAbility(new ExileAndReturnSourceEffect(
                PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.SHE,
                false, new CreateTokenEffect(new ZombieToken())
        ), false, filter));

        // Liliana, Defiant Necromancer
        // +2: Each player discards a card.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new DiscardEachPlayerEffect(1, false), 2));

        // -X: Return target nonlegendary creature with converted mana cost X from your graveyard to the battlefield.
        Ability ability = new LoyaltyAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter2));
        ability.setTargetAdjuster(LilianaDefiantNecromancerAdjuster.instance);
        this.getRightHalfCard().addAbility(ability);

        //-8: You get an emblem with "Whenever a creature dies, return it to the battlefield under your control at the beginning of the next end step.";
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new GetEmblemEffect(new LilianaDefiantNecromancerEmblem()), -8));
    }

    private LilianaHereticalHealer(final LilianaHereticalHealer card) {
        super(card);
    }

    @Override
    public LilianaHereticalHealer copy() {
        return new LilianaHereticalHealer(this);
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
        FilterCard newFilter = LilianaHereticalHealer.filter2.copy();
        newFilter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, cmc));
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(newFilter));
    }
}
