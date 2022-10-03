package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.game.Game;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AyeshaTanakaArmorer extends CardImpl {

    private static final FilterCard filter = new FilterArtifactCard(
            "artifact cards with mana value less than or equal to {this}'s power"
    );
    private static final FilterPermanent filter2 = new FilterControlledArtifactPermanent();

    static {
        filter.add(AyeshaTanakaArmorerPredicate.instance);
        filter2.add(DefendingPlayerControlsPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            filter2, ComparisonType.MORE_THAN, 2, false
    );

    public AyeshaTanakaArmorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Ayesha Tanaka, Armorer attacks, look at the top four cards of your library. You may put any number of artifact cards with mana value less than or equal to Ayesha's power from among them onto the battlefield tapped. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksTriggeredAbility(new LookLibraryAndPickControllerEffect(
                4, Integer.MAX_VALUE, filter,
                LookLibraryControllerEffect.PutCards.BATTLEFIELD_TAPPED,
                LookLibraryControllerEffect.PutCards.BOTTOM_RANDOM
        )));

        // Ayesha can't be blocked as long as defending player controls three or more artifacts.
        this.addAbility(new SimpleStaticAbility(new ConditionalRestrictionEffect(
                new CantBeBlockedSourceEffect(), condition, "{this} can't be blocked " +
                "as long as defending player controls three or more artifacts"
        )));
    }

    private AyeshaTanakaArmorer(final AyeshaTanakaArmorer card) {
        super(card);
    }

    @Override
    public AyeshaTanakaArmorer copy() {
        return new AyeshaTanakaArmorer(this);
    }
}

enum AyeshaTanakaArmorerPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .map(p -> input.getObject().getManaValue() <= p)
                .orElse(false);
    }
}