package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.command.emblems.LilianaDefiantNecromancerEmblem;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LilianaHereticalHealer extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another nontoken creature you control");
    private static final FilterCreatureCard graveyardFilter = new FilterCreatureCard("nonlegendary creature card with mana value X from your graveyard");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
        graveyardFilter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public LilianaHereticalHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "{1}{B}{B}",
                "Liliana, Defiant Necromancer",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.PLANESWALKER}, new SubType[]{SubType.LILIANA}, "B"
        );

        // Liliana, Heretical Healer
        this.getLeftHalfCard().setPT(2, 3);

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // Whenever another nontoken creature you control dies, exile Liliana Heretical Healer, then return her to the battlefield transformed under her owner's control. If you do, create a 2/2 black Zombie creature token.
        this.getLeftHalfCard().addAbility(new DiesCreatureTriggeredAbility(new ExileAndReturnSourceEffect(
                PutCards.BATTLEFIELD_TRANSFORMED, Pronoun.SHE, false, new CreateTokenEffect(new ZombieToken())
        ), false, filter));

        // Liliana, Defiant Necromancer
        this.getRightHalfCard().setStartingLoyalty(3);

        // +2: Each player discards a card.
        this.getRightHalfCard().addAbility(new LoyaltyAbility(new DiscardEachPlayerEffect(1, false), 2));

        // -X: Return target nonlegendary creature with mana value X from your graveyard to the battlefield.
        Ability ability = new LoyaltyAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(graveyardFilter));
        ability.setTargetAdjuster(new XManaValueTargetAdjuster());
        this.getRightHalfCard().addAbility(ability);

        // -8: You get an emblem with "Whenever a creature dies, return it to the battlefield under your control at the beginning of the next end step."
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
