package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WarpAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AstelliReclaimer extends CardImpl {

    private static final FilterCard filter = new FilterCard(
            "noncreature, nonland permanent card with mana value X or less"
    );

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(PermanentPredicate.instance);
        filter.add(AstelliReclaimerPredicate.instance);
    }

    public AstelliReclaimer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, return target noncreature, nonland permanent card with mana value X or less from your graveyard to the battlefield, where X is the amount of mana spent to cast this creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect()
                .setText("return target noncreature, nonland permanent card with mana value X or less from " +
                        "your graveyard to the battlefield, where X is the amount of mana spent to cast this creature"));
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);

        // Warp {2}{W}
        this.addAbility(new WarpAbility(this, "{2}{W}"));
    }

    private AstelliReclaimer(final AstelliReclaimer card) {
        super(card);
    }

    @Override
    public AstelliReclaimer copy() {
        return new AstelliReclaimer(this);
    }
}

enum AstelliReclaimerPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue() <= ManaPaidSourceWatcher.getTotalPaid(input.getSourceId(), game);
    }
}
