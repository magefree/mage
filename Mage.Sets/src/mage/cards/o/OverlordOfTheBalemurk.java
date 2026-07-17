package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.keyword.ImpendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverlordOfTheBalemurk extends CardImpl {

    private static final FilterCard filter = new FilterCard("non-Avatar creature card or a planeswalker card from your graveyard");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        Predicates.not(SubType.AVATAR.getPredicate()),
                        CardType.CREATURE.getPredicate()
                ), CardType.PLANESWALKER.getPredicate()
        ));
    }

    public OverlordOfTheBalemurk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{B}{B}");

        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Impending 5--{1}{B}
        this.addAbility(new ImpendingAbility(5, "{1}{B}"));

        // Whenever Overlord of the Balemurk enters or attacks, mill four cards, then you may return a non-Avatar creature card or a planeswalker card from your graveyard to your hand.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new MillCardsControllerEffect(4));
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(true, filter, PutCards.HAND).concatBy(", then"));
        this.addAbility(ability);
    }

    private OverlordOfTheBalemurk(final OverlordOfTheBalemurk card) {
        super(card);
    }

    @Override
    public OverlordOfTheBalemurk copy() {
        return new OverlordOfTheBalemurk(this);
    }
}
