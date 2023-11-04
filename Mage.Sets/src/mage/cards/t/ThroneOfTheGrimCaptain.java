package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SubTypeAssignment;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInGraveyardBattlefieldOrStack;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ThroneOfTheGrimCaptain extends CardImpl {

    public ThroneOfTheGrimCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.secondSideCardClazz = mage.cards.t.TheGrimCaptain.class;

        // {T}: Mill two cards.
        this.addAbility(new SimpleActivatedAbility(new MillCardsControllerEffect(2), new TapSourceCost()));

        // Craft with a Dinosaur, a Merfolk, a Pirate, and a Vampire {4}
        this.addAbility(new CraftAbility("{4}", "a Dinosaur, a Merfolk, a Pirate, and a Vampire", new ThroneOfTheGrimCaptainTarget()));
    }

    private ThroneOfTheGrimCaptain(final ThroneOfTheGrimCaptain card) {
        super(card);
    }

    @Override
    public ThroneOfTheGrimCaptain copy() {
        return new ThroneOfTheGrimCaptain(this);
    }
}

class ThroneOfTheGrimCaptainTarget extends TargetCardInGraveyardBattlefieldOrStack {

    private static final FilterCard filterCard =
            new FilterCard("a Dinosaur, a Merfolk, a Pirate, or Vampire card");
    private static final FilterPermanent filterPermanent =
            new FilterControlledPermanent("another Dinosaur, a Merfolk, a Pirate, or Vampire you control");
    private static final Predicate<MageObject> predicate = Predicates.or(
            SubType.PIRATE.getPredicate(),
            SubType.VAMPIRE.getPredicate(),
            SubType.DINOSAUR.getPredicate(),
            SubType.MERFOLK.getPredicate()
    );

    static {
        filterCard.add(predicate);
        filterCard.add(TargetController.YOU.getOwnerPredicate());
        filterPermanent.add(predicate);
        filterPermanent.add(AnotherPredicate.instance);
    }

    private static final SubTypeAssignment subtypeAssigner = new SubTypeAssignment(
            SubType.PIRATE, SubType.VAMPIRE, SubType.DINOSAUR, SubType.MERFOLK
    );

    ThroneOfTheGrimCaptainTarget() {
        super(4, 4, filterCard, filterPermanent);
    }

    private ThroneOfTheGrimCaptainTarget(final ThroneOfTheGrimCaptainTarget target) {
        super(target);
    }

    @Override
    public ThroneOfTheGrimCaptainTarget copy() {
        return new ThroneOfTheGrimCaptainTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Set<Card> cards = this.getTargets().stream().map(uuid -> {
            Permanent permanent = game.getPermanent(uuid);
            if (permanent != null) {
                return permanent;
            }
            return game.getCard(uuid);
        }).filter(Objects::nonNull).collect(Collectors.toSet());
        return subtypeAssigner.getRoleCount(cards, game) <= 4;
    }
}
