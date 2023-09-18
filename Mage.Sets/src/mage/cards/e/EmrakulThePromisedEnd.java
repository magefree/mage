package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.cost.SpellCostReductionForEachSourceEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class EmrakulThePromisedEnd extends CardImpl {

    private static final FilterCard filter = new FilterCard("instants");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public EmrakulThePromisedEnd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{13}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(13);
        this.toughness = new MageInt(13);

        // Emrakul, the Promised End costs {1} less to cast for each card type among cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new SpellCostReductionForEachSourceEffect(
                        1, CardTypesInGraveyardCount.YOU
                ).setText("this spell costs {1} less to cast for each card type among cards in your graveyard")
        ).setRuleAtTheTop(true).addHint(CardTypesInGraveyardHint.YOU));

        // When you cast Emrakul, you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn.
        Ability ability = new CastSourceTriggeredAbility(new EmrakulThePromisedEndGainControlEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Protection from instants
        this.addAbility(new ProtectionAbility(filter));
    }

    private EmrakulThePromisedEnd(final EmrakulThePromisedEnd card) {
        super(card);
    }

    @Override
    public EmrakulThePromisedEnd copy() {
        return new EmrakulThePromisedEnd(this);
    }
}

class EmrakulThePromisedEndGainControlEffect extends OneShotEffect {

    EmrakulThePromisedEndGainControlEffect() {
        super(Outcome.GainControl);
        this.staticText = "you gain control of target opponent during that player's next turn. After that turn, that player takes an extra turn";
    }

    private EmrakulThePromisedEndGainControlEffect(final EmrakulThePromisedEndGainControlEffect effect) {
        super(effect);
    }

    @Override
    public EmrakulThePromisedEndGainControlEffect copy() {
        return new EmrakulThePromisedEndGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            TurnMod extraTurnMod = new TurnMod(targetPlayer.getId()).withExtraTurn();
            TurnMod newControllerTurnMod = new TurnMod(targetPlayer.getId()).withNewController(controller.getId(), extraTurnMod);
            game.getState().getTurnMods().add(newControllerTurnMod);
            return true;
        }
        return false;
    }
}
