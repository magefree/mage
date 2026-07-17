package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class PracticedScrollsmith extends CardImpl {

    private static final FilterCard filter = new FilterNonlandCard("a noncreature, nonland card");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public PracticedScrollsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R/W}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When this creature enters, exile target noncreature, nonland card from your graveyard. Until the end of your next turn, you may cast that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PracticedScrollsmithEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private PracticedScrollsmith(final PracticedScrollsmith card) {
        super(card);
    }

    @Override
    public PracticedScrollsmith copy() {
        return new PracticedScrollsmith(this);
    }
}

class PracticedScrollsmithEffect extends OneShotEffect {

    PracticedScrollsmithEffect() {
        super(Outcome.Benefit);
        staticText = "exile target noncreature, nonland card from your graveyard. " +
            "Until the end of your next turn, you may cast that card";
    }

    private PracticedScrollsmithEffect(final PracticedScrollsmithEffect effect) {
        super(effect);
    }

    @Override
    public PracticedScrollsmithEffect copy() {
        return new PracticedScrollsmithEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        if (card == null) {
            return false;
        }
        PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(
            game, source, card, TargetController.YOU,
            Duration.UntilEndOfYourNextTurn,
            false, false, true
        );
        return true;
    }
}
