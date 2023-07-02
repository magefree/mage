package mage.cards.g;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TiagoMDG
 */
public final class GandalfWhiteRider extends CardImpl {

    public GandalfWhiteRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast a spell, creatures you control get +1/+0 until end of turn. Scry 1.
        // When Gandalf, White Rider dies, you may put it into its owner's library fifth from the top.

        // Adds ability that whenever you cast a spell it gives a temporary +1/+0 effect until end of turn
        Ability ability = new SpellCastControllerTriggeredAbility(
                new BoostControlledEffect(1, 0, Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_A,
                false
        );

        // Add scry effect to ability
        ability.addEffect(new ScryEffect(1, false));

        this.addAbility(ability);
        this.addAbility(new DiesSourceTriggeredAbility(new GandalfWhiteRiderDyingEffect(), true));
    }

    private GandalfWhiteRider(final GandalfWhiteRider card) {
        super(card);
    }

    @Override
    public GandalfWhiteRider copy() {
        return new GandalfWhiteRider(this);
    }
}

class GandalfWhiteRiderDyingEffect extends OneShotEffect {

    public GandalfWhiteRiderDyingEffect() {
        super(Outcome.ReturnToHand);
        staticText = "put it into its owner's library fifth from the top";
    }

    public GandalfWhiteRiderDyingEffect(final GandalfWhiteRiderDyingEffect effect) {
        super(effect);
    }

    @Override
    public GandalfWhiteRiderDyingEffect copy() {
        return new GandalfWhiteRiderDyingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        if (controller == null) {
            return false;
        }

        Card card = game.getCard(source.getSourceId());
        Card zoneCounter = (Card) getValue("permanentLeftBattlefield");

        if (card == null || card.getZoneChangeCounter(game) - 1 != zoneCounter.getZoneChangeCounter(game)) {
            return false;
        }
        return controller.putCardOnTopXOfLibrary(card, game, source, 5, true);
    }
}
