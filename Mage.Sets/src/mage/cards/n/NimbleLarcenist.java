package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileCardYouChooseTargetOpponentEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NimbleLarcenist extends CardImpl {

    private static final FilterCard filter = new FilterCard("an artifact, instant, or sorcery card");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()
        ));
    }

    public NimbleLarcenist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}{B}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Nimble Larcenist enters the battlefield, target opponent reveals their hand. You choose an artifact, instant, or sorcery card from it and exile that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileCardYouChooseTargetOpponentEffect(filter));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private NimbleLarcenist(final NimbleLarcenist card) {
        super(card);
    }

    @Override
    public NimbleLarcenist copy() {
        return new NimbleLarcenist(this);
    }
}
