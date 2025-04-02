package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.abilities.Ability;
import mage.abilities.assignment.common.ColorAssignment;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author androosss
 */
public final class LotuslightDancers extends CardImpl {

    public LotuslightDancers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}{U}");
        
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When this creature enters, search your library for a black card, a green card, and a blue card. Put those cards into your graveyard, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LotuslightDancersEffect()));
    }

    private LotuslightDancers(final LotuslightDancers card) {
        super(card);
    }

    @Override
    public LotuslightDancers copy() {
        return new LotuslightDancers(this);
    }
}

class LotuslightDancersEffect extends OneShotEffect {

    LotuslightDancersEffect() {
        super(Outcome.Neutral);
        staticText = "search your library for a black card, a green card, and a blue card. " +
                "Put those cards into your graveyard, then shuffle.";
    }

    private LotuslightDancersEffect(final LotuslightDancersEffect effect) {
        super(effect);
    }

    @Override
    public LotuslightDancersEffect copy() {
        return new LotuslightDancersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInLibrary target = new LotuslightDancersTarget();
        controller.searchLibrary(target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        cards.retainZone(Zone.LIBRARY, game);
        controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }
}

class LotuslightDancersTarget extends TargetCardInLibrary {

    private static final FilterCard filter
            = new FilterCard("a black card, a green card, and a blue card");

    static {
        filter.add(Predicates.or(
            new ColorPredicate(ObjectColor.BLUE),
            new ColorPredicate(ObjectColor.BLACK),
            new ColorPredicate(ObjectColor.GREEN)
        ));
    }

    private static final ColorAssignment colorAssigner = new ColorAssignment("U", "B", "G");

    LotuslightDancersTarget() {
        super(0, 3, filter);
    }

    private LotuslightDancersTarget(final LotuslightDancersTarget target) {
        super(target);
    }

    @Override
    public LotuslightDancersTarget copy() {
        return new LotuslightDancersTarget(this);
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
        return colorAssigner.getRoleCount(cards, game) >= cards.size();
    }
}