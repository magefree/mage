package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilites.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.AffinityForArtifactsAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterArtifactCard;

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


	private static final FilterArtifactCard filter = new FilterArtifactCard("an artifact spell");
	
	public ChissGoriaForgeTyrantEffect() {
	    super(Outcome.Benefit);
	    staticText = "exile the top five cards of your library. " 
		+ "You may cast an artifact spell from among them this turn. ";
	}

	private ChissGoriaForgeTyrantEffect(final ChissGoriaForgeTyrantEffect effect) {
	    super(effect);
	}

	@Override
	public ChissGoryaForgeTyrantEffect copy() {
		return new ChissGoryaForgeTyrantEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player controller = game.getPlayer(source.getControllerId());
		if (controller == null) {
			return false;
		}
		
		// move top 5 from library to exile
		Cards cards = new CardsImpl(controller.getLibrary().getFromTop(game, 5));
		controller.moveCards(cards, Zone.EXILED, source, game);

		// cast one with affinity for artifacts
		Ability ability = new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new AffinityForArtifactsAbility(), filter).setText("If you do, it has affinity for artifacts."));
		cards.addAbility(ability);
		CardUtil.castSingle(controller, source, game, cards);

		return true;
	}
}

		
		
