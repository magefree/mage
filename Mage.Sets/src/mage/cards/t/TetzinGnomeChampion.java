package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CraftAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.DoubleFacedCardPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.MilledCardsEvent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth
 */
public class TetzinGnomeChampion extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another double-faced artifact");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.and(
                DoubleFacedCardPredicate.instance,
                CardType.ARTIFACT.getPredicate()
        )
        );
    }

    public TetzinGnomeChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{U}{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.GNOME);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.t.TheGoldenGearColossus.class;
        this.color.setBlue(true);
        this.color.setRed(true);
        this.color.setWhite(true);

        // Whenever Tetzin or another double-faced artifact enters the battlefield under your control, mill three cards. You may put an artifact card from among them into your hand.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(new TetzinGnomeChampionEffect(), filter, false, true));

        // Craft with six artifacts 4 (4, Exile this artifact, Exile the six from among other permanents you control and/or cards from your graveyard: Return this card transformed under its owner's control. Craft only as a sorcery.)
        this.addAbility(new CraftAbility(
                "{4}", "six artifacts", "other artifacts you control and/or"
                + "artifact cards in your graveyard", 6, 6, CardType.ARTIFACT.getPredicate()
        ));
    }

    private TetzinGnomeChampion(final TetzinGnomeChampion card) {
        super(card);
    }

    @Override
    public TetzinGnomeChampion copy() {
        return new TetzinGnomeChampion(this);
    }

}

class TetzinGnomeChampionEffect extends OneShotEffect {

    TetzinGnomeChampionEffect() {
        super(Outcome.Benefit);
        staticText = "mill three cards. You may put an artifact card from among them into your hand.";
    }

    private TetzinGnomeChampionEffect(final TetzinGnomeChampionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterArtifactCard filter = new FilterArtifactCard();
        TargetCard target = new TargetCard(Zone.GRAVEYARD, filter);

        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            GameEvent event = GameEvent.getEvent(GameEvent.EventType.MILL_CARDS, getId(), source, getId(), 3);
            if (game.replaceEvent(event)) {
                return false;
            }
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, event.getAmount()));
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            for (Card card : cards.getCards(game)) {
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.MILLED_CARD, card.getId(), source, controller.getId()));
            }
            game.fireEvent(new MilledCardsEvent(source, getId(), cards));
            if (target.canChoose(controller.getId(), game)
                    && controller.chooseUse(Outcome.Benefit, "Do you wish to keep an artifact card from among the milled cards?", source, game)
                    && controller.choose(Outcome.Benefit, cards, target, source, game)) {
                if (target.getFirstTarget() != null) {
                    Card targetArtifact = game.getCard(target.getFirstTarget());
                    controller.moveCardToHandWithInfo(targetArtifact, source, game, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public TetzinGnomeChampionEffect copy() {
        return new TetzinGnomeChampionEffect(this);
    }
}
