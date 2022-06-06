package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.SubTypeAssignment;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HarperRecruiter extends CardImpl {

    public HarperRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Harper Recruiter attacks, look at the top four cards of your library. You may reveal a Cleric card, a Rogue card, a Warrior card, and/or a Wizard card from among them and put those cards into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new AttacksTriggeredAbility(new HarperRecruiterEffect()));
    }

    private HarperRecruiter(final HarperRecruiter card) {
        super(card);
    }

    @Override
    public HarperRecruiter copy() {
        return new HarperRecruiter(this);
    }
}

class HarperRecruiterEffect extends OneShotEffect {

    HarperRecruiterEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top four cards of your library. You may reveal a Cleric card, a Rogue card, " +
                "a Warrior card, and/or a Wizard card from among them and put those cards into your hand. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private HarperRecruiterEffect(final HarperRecruiterEffect effect) {
        super(effect);
    }

    @Override
    public HarperRecruiterEffect copy() {
        return new HarperRecruiterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        TargetCard target = new HarperRecruiterTarget();
        player.choose(outcome, cards, target, game);
        Cards toHand = new CardsImpl(target.getTargets());
        player.revealCards(source, toHand, game);
        player.moveCards(toHand, Zone.HAND, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}

class HarperRecruiterTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a Cleric card, a Rogue card, a Warrior card, and/or a Wizard card");

    static {
        filter.add(Predicates.or(
                SubType.CLERIC.getPredicate(),
                SubType.ROGUE.getPredicate(),
                SubType.WARRIOR.getPredicate(),
                SubType.WIZARD.getPredicate()
        ));
    }

    private static final SubTypeAssignment subTypeAssigner = new SubTypeAssignment(
            SubType.CLERIC,
            SubType.ROGUE,
            SubType.WARRIOR,
            SubType.WIZARD
    );

    HarperRecruiterTarget() {
        super(0, 4, filter);
    }

    private HarperRecruiterTarget(final HarperRecruiterTarget target) {
        super(target);
    }

    @Override
    public HarperRecruiterTarget copy() {
        return new HarperRecruiterTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        if (this.getTargets().isEmpty()) {
            return true;
        }
        Cards cards = new CardsImpl(this.getTargets());
        cards.add(card);
        return subTypeAssigner.getRoleCount(cards, game) >= cards.size();
    }
}
