package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoorlandRescuer extends CardImpl {

    public MoorlandRescuer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Moorland Rescuer dies, return any number of other creature cards with total power X or less from your graveyard to the battlefield, where X is Moorland Rescuer's power. Exile Moorland Rescuer.
        this.addAbility(new DiesSourceTriggeredAbility(new MoorlandRescuerEffect()));
    }

    private MoorlandRescuer(final MoorlandRescuer card) {
        super(card);
    }

    @Override
    public MoorlandRescuer copy() {
        return new MoorlandRescuer(this);
    }
}

class MoorlandRescuerEffect extends OneShotEffect {

    MoorlandRescuerEffect() {
        super(Outcome.Benefit);
        staticText = "return any number of other creature cards with total power X or less " +
                "from your graveyard to the battlefield, where X is {this}'s power. Exile {this}";
    }

    private MoorlandRescuerEffect(final MoorlandRescuerEffect effect) {
        super(effect);
    }

    @Override
    public MoorlandRescuerEffect copy() {
        return new MoorlandRescuerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) getValue("permanentLeftBattlefield");
        if (player == null || permanent == null) {
            return false;
        }
        TargetCard target = new MoorlandRescuerTarget(permanent.getPower().getValue(), source, game);
        player.choose(outcome, player.getGraveyard(), target, source, game);
        player.moveCards(new CardsImpl(target.getTargets()), Zone.BATTLEFIELD, source, game);
        Card sourceCard = (Card) source.getSourceObjectIfItStillExists(game);
        if (sourceCard != null) {
            player.moveCards(sourceCard, Zone.EXILED, source, game);
        }
        return true;
    }
}

class MoorlandRescuerTarget extends TargetCardInYourGraveyard {

    private final int xValue;

    MoorlandRescuerTarget(int xValue, Ability source, Game game) {
        super(0, Integer.MAX_VALUE, makeFilter(xValue, source, game));
        this.xValue = xValue;
        this.notTarget = true;
    }

    private MoorlandRescuerTarget(final MoorlandRescuerTarget target) {
        super(target);
        this.xValue = target.xValue;
    }

    @Override
    public MoorlandRescuerTarget copy() {
        return new MoorlandRescuerTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        if (card == null) {
            return false;
        }
        int powerSum = this
                .getTargets()
                .stream()
                .map(game::getCard)
                .map(Card::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
        return card.getPower().getValue() + powerSum <= xValue;
    }

    private static FilterCard makeFilter(int xValue, Ability source, Game game) {
        FilterCard filter = new FilterCreatureCard(
                "creature cards with total power " + xValue + " or less from your graveyard"
        );
        filter.add(Predicates.not(new MageObjectReferencePredicate(source.getSourceObject(game), game)));
        return filter;
    }
}
