package mage.cards.p;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class PaladinElizabethTaggerdy extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card with mana value X or less");

    static {
        filter.add(PaladinElizabethTaggerdyPredicate.instance);
    }

    public PaladinElizabethTaggerdy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Battalion -- Whenever Paladin Elizabeth Taggerdy and at least two other creatures attack, draw a card, then you may put a creature card with mana value X or less from your hand onto the battlefield tapped and attacking, where X is Paladin Elizabeth Taggerdy's power.
        Ability ability = new BattalionAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new PutCardFromHandOntoBattlefieldEffect(
                filter, false, true, true
        ).concatBy(", then"));
        ability.addEffect(new InfoEffect(", where X is {this}'s power"));
        this.addAbility(ability);
    }

    private PaladinElizabethTaggerdy(final PaladinElizabethTaggerdy card) {
        super(card);
    }

    @Override
    public PaladinElizabethTaggerdy copy() {
        return new PaladinElizabethTaggerdy(this);
    }
}

enum PaladinElizabethTaggerdyPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .filter(power -> input.getObject().getManaValue() <= power)
                .isPresent();
    }
}
