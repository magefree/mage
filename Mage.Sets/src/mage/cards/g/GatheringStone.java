package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author muz
 */
public final class GatheringStone extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells you cast of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.TRUE);
    }

    public GatheringStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // As this artifact enters, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit)));

        // Spells you cast of the chosen type cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // When this artifact enters and at the beginning of your upkeep, look at the top card of your library.
        // If it's a card of the chosen type, you may reveal it and put it into your hand.
        // If you don't put the card into your hand, you may put it into your graveyard.
        this.addAbility(new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new GatheringStoneEffect(),
                false,
                "When this artifact enters and at the beginning of your upkeep, ",
                new EntersBattlefieldTriggeredAbility(null),
                new BeginningOfUpkeepTriggeredAbility(null)
        ));
    }

    private GatheringStone(final GatheringStone card) {
        super(card);
    }

    @Override
    public GatheringStone copy() {
        return new GatheringStone(this);
    }
}

class GatheringStoneEffect extends OneShotEffect {

    GatheringStoneEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. " +
                "If it's a card of the chosen type, you may reveal it and put it into your hand. " +
                "If you don't put the card into your hand, you may put it into your graveyard";
    }

    private GatheringStoneEffect(final GatheringStoneEffect effect) {
        super(effect);
    }

    @Override
    public GatheringStoneEffect copy() {
        return new GatheringStoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card topCard = controller.getLibrary().getFromTop(game);
        if (topCard == null) {
            return false;
        }
        SubType subtype = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        controller.lookAtCards("Top card of library", topCard, game);
        if (topCard.hasSubtype(subtype, game) && controller.chooseUse(Outcome.DrawCard, "Reveal " + topCard.getName() + " and put it into your hand?", source, game)) {
            controller.revealCards(source, new CardsImpl(topCard), game);
            controller.moveCards(topCard, Zone.HAND, source, game);
            return true;
        }
        if (controller.chooseUse(Outcome.Neutral, "Put " + topCard.getName() + " into your graveyard?", source, game)) {
            controller.moveCards(topCard, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}
