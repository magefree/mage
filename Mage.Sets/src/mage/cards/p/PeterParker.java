package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WebSlingingAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.game.permanent.token.Spider21Token;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class PeterParker extends ModalDoubleFacedCard {

    public PeterParker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY},new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SCIENTIST, SubType.HERO}, "{1}{W}",
                "Amazing Spider-Man",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SPIDER, SubType.HUMAN, SubType.HERO}, "{1}{G}{W}{U}");

        this.getLeftHalfCard().setPT(0, 1);
        this.getRightHalfCard().setPT(4, 4);

        // When Peter Parker enters, create a 2/1 green Spider creature token with reach.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Spider21Token())));

        // {1}{G}{W}{U}: Transform Peter Parker. Activate only as a sorcery.
        this.getLeftHalfCard().addAbility(new ActivateAsSorceryActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{1}{G}{W}{U}")
        ));

        // Amazing Spider-Man
        // Vigilance
        this.getRightHalfCard().addAbility(VigilanceAbility.getInstance());

        // Reach
        this.getRightHalfCard().addAbility(ReachAbility.getInstance());

        // Each legendary spell you cast that's one or more colors has web-slinging {G}{W}{U}.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new AmazingSpiderManEffect()));
    }

    private PeterParker(final PeterParker card) {
        super(card);
    }

    @Override
    public PeterParker copy() {
        return new PeterParker(this);
    }
}
class AmazingSpiderManEffect extends ContinuousEffectImpl {

    static final FilterCard filter = new FilterCard("legendary spell that's one or more colors");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    AmazingSpiderManEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each legendary spell you cast that's one or more colors has web-slinging {G}{W}{U}";
    }

    private AmazingSpiderManEffect(final AmazingSpiderManEffect effect) {
        super(effect) ;
    }

    @Override
    public AmazingSpiderManEffect copy() {
        return new AmazingSpiderManEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Set<Card> cardsToGainAbility = new HashSet<>();
        cardsToGainAbility.addAll(controller.getHand().getCards(filter, game));
        cardsToGainAbility.addAll(controller.getGraveyard().getCards(filter, game));
        controller.getLibrary().getCards(game).stream()
                .filter(c -> filter.match(c, game))
                .forEach(cardsToGainAbility::add);
        game.getExile().getCardsInRange(game, controller.getId()).stream()
                .filter(c -> filter.match(c, game))
                .forEach(cardsToGainAbility::add);
        game.getCommanderCardsFromCommandZone(controller, CommanderCardType.ANY).stream()
                .filter(c -> filter.match(c, game))
                .forEach(cardsToGainAbility::add);
        game.getStack().stream()
                .filter(Spell.class::isInstance)
                .filter(s -> s.isControlledBy(controller.getId()))
                .filter(s -> filter.match((Spell) s, game))
                .map(s -> game.getCard(s.getSourceId()))
                .filter(Objects::nonNull)
                .forEach(cardsToGainAbility::add);
        for (Card card : cardsToGainAbility) {
            Ability ability = new WebSlingingAbility(card, "{G}{W}{U}");
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getControllerOrOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }
}
