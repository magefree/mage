package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.assignment.common.SubTypeAssignment;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInGraveyardBattlefieldOrStack;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ThroneOfTheGrimCaptain extends TransformingDoubleFacedCard {

    public ThroneOfTheGrimCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{2}",
                "The Grim Captain",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SKELETON, SubType.SPIRIT, SubType.PIRATE}, "B"
        );

        // Throne of the Grim Captain
        // {T}: Mill two cards.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new MillCardsControllerEffect(2), new TapSourceCost()));

        // Craft with a Dinosaur, a Merfolk, a Pirate, and a Vampire {4}
        this.getLeftHalfCard().addAbility(new CraftAbility("{4}", "a Dinosaur, a Merfolk, a Pirate, and a Vampire", new ThroneOfTheGrimCaptainTarget()));

        // The Grim Captain
        this.getRightHalfCard().setPT(7, 7);

        // Menace
        this.getRightHalfCard().addAbility(new MenaceAbility(false));

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());

        // Hexproof
        this.getRightHalfCard().addAbility(HexproofAbility.getInstance());

        // Whenever The Grim Captain attacks, each opponent sacrifices a nonland permanent. Then you may put an exiled creature card used to craft The Grim Captain onto the battlefield under your control tapped and attacking.
        Ability ability = new AttacksTriggeredAbility(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_NON_LAND));
        ability.addEffect(new TheGrimCaptainEffect());
        this.getRightHalfCard().addAbility(ability);
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

class TheGrimCaptainEffect extends OneShotEffect {

    TheGrimCaptainEffect() {
        super(Outcome.Benefit);
        staticText = "Then you may put an exiled creature card used to craft {this} " +
                "onto the battlefield under your control tapped and attacking";
    }

    private TheGrimCaptainEffect(final TheGrimCaptainEffect effect) {
        super(effect);
    }

    @Override
    public TheGrimCaptainEffect copy() {
        return new TheGrimCaptainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInExile(
                0, 1, StaticFilters.FILTER_CARD_CREATURE,
                CardUtil.getExileZoneId(game, source, -2)
        );
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return false;
        }
        game.getCombat().addAttackingCreature(card.getId(), game);
        return true;
    }
}
