package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.game.command.emblems.LilianaDefiantNecromancerEmblem;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XManaValueTargetAdjuster;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class LilianaDefiantNecromancer extends CardImpl {

    protected static final FilterCreatureCard filter = new FilterCreatureCard("nonlegendary creature card with mana value X from your graveyard");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public LilianaDefiantNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);
        this.color.setBlack(true);

        this.nightCard = true;

        this.setStartingLoyalty(3);

        // +2: Each player discards a card.
        this.addAbility(new LoyaltyAbility(new DiscardEachPlayerEffect(1, false), 2));

        // -X: Return target nonlegendary creature with converted mana cost X from your graveyard to the battlefield.
        Ability ability = new LoyaltyAbility(new ReturnFromGraveyardToBattlefieldTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.setTargetAdjuster(new XManaValueTargetAdjuster());
        this.addAbility(ability);

        //-8: You get an emblem with "Whenever a creature dies, return it to the battlefield under your control at the beginning of the next end step.";
        this.addAbility(new LoyaltyAbility(new GetEmblemEffect(new LilianaDefiantNecromancerEmblem()), -8));
    }

    private LilianaDefiantNecromancer(final LilianaDefiantNecromancer card) {
        super(card);
    }

    @Override
    public LilianaDefiantNecromancer copy() {
        return new LilianaDefiantNecromancer(this);
    }
}
