package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.asthought.CanPlayCardControllerEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.Cards;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardsImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * @author MorellThomas
 */
public final class ChissGoriaForgeTyrant extends CardImpl {

    public ChissGoriaForgeTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}{R}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Affinity for artifacts
	this.addAbility(new AffinityForArtifactsAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Chiss-Goria, Forge Tyrant attacks, exile the top five cards of your library. You may cast an artifact spell from among them this turn. If you do, it has affinity for artifacts.
	this.addAbility(new AttacksTriggeredAbility(new ChissGoriaForgeTyrantEffect(), false));

    }

    private ChissGoriaForgeTyrant(final ChissGoriaForgeTyrant card) {
        super(card);
    }

    @Override
    public ChissGoriaForgeTyrant copy() {
        return new ChissGoriaForgeTyrant(this);
    }
}

class ChissGoriaForgeTyrantEffect extends OneShotEffect {
	
	public ChissGoriaForgeTyrantEffect() {
	    super(Outcome.Benefit);
	    staticText = "exile the top five cards of your library. " 
		+ "You may cast an artifact spell from among them this turn. "
		+ "If you do, it has affinity for artifacts.";
	}

	private ChissGoriaForgeTyrantEffect(final ChissGoriaForgeTyrantEffect effect) {
	    super(effect);
	}

	@Override
	public ChissGoriaForgeTyrantEffect copy() {
		return new ChissGoriaForgeTyrantEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player controller = game.getPlayer(source.getControllerId());
		if (controller == null) {
			return false;
		}
		
		// move top 5 from library to exile
		Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
		if (cards.isEmpty()) {
			return false;
		}

		controller.moveCards(cards, Zone.EXILED, source, game);
		for (Card card : cards.getCards(game)) {
			card.addAbility(new AffinityForArtifactsAbility());
			game.addEffect(new ChissGoriaForgeTyrantCastEffect(game, card), source);
		}

		return true;
	}
}

// cast with affinity for artifacts
class ChissGoriaForgeTyrantCastEffect extends CanPlayCardControllerEffect {


	ChissGoriaForgeTyrantCastEffect(Game game, Card card) {
		super(game, card.getId(), card.getZoneChangeCounter(game), Duration.EndOfTurn);
	}

	private ChissGoriaForgeTyrantCastEffect(final ChissGoriaForgeTyrantCastEffect effect) {
		super(effect);
	}

	@Override
	public ChissGoriaForgeTyrantCastEffect copy() {
		return new ChissGoriaForgeTyrantCastEffect(this);
	}
	
	@Override
	public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
		return super.applies(sourceId, source, affectedControllerId, game) && game.getCard(sourceId).hasCardTypeForDeckbuilding(CardType.ARTIFACT);
	}
}

		
		
