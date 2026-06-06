package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.WishEffect;
import mage.abilities.hint.common.OpenSideboardHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaeOfWishes extends AdventureCard {

    public FaeOfWishes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.FAERIE, SubType.WIZARD}, "{1}{U}",
                "Granted",
                new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Fae of Wishes
        this.getLeftHalfCard().setPT(1, 4);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // {1}{U}, Discard two cards: Return Fae of Wishes to its owner's hand.
        Ability ability = new SimpleActivatedAbility(
                new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{1}{U}")
        );
        ability.addCost(new DiscardTargetCost(new TargetCardInHand(2, StaticFilters.FILTER_CARD_CARDS)));
        this.getLeftHalfCard().addAbility(ability);

        // Granted
        // You may reveal a noncreature card you own from outside the game and put it into your hand.
        this.getRightHalfCard().getSpellAbility().addEffect(new WishEffect(StaticFilters.FILTER_CARD_A_NON_CREATURE));
        this.getRightHalfCard().getSpellAbility().addHint(OpenSideboardHint.instance);

        finalizeCard();
    }

    private FaeOfWishes(final FaeOfWishes card) {
        super(card);
    }

    @Override
    public FaeOfWishes copy() {
        return new FaeOfWishes(this);
    }
}
