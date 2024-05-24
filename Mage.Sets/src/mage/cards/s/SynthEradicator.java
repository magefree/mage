package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SynthEradicator extends CardImpl {

    public SynthEradicator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.SYNTH);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Synth Eradicator attacks, exile the top card of your library. You may get {E}{E}. If you don't, you may play that card this turn.
        this.addAbility(new AttacksTriggeredAbility(new SynthEradicatorEffect()));

        // {T}, Pay {E}{E}{E}: Synth Eradicator deals 3 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(3), new TapSourceCost());
        ability.addCost(new PayEnergyCost(3));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SynthEradicator(final SynthEradicator card) {
        super(card);
    }

    @Override
    public SynthEradicator copy() {
        return new SynthEradicator(this);
    }
}

class SynthEradicatorEffect extends OneShotEffect {

    SynthEradicatorEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. You may get {E}{E}. "
                + "If you don't, you may play that card this turn.";
    }

    private SynthEradicatorEffect(final SynthEradicatorEffect effect) {
        super(effect);
    }

    @Override
    public SynthEradicatorEffect copy() {
        return new SynthEradicatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        String mainMessage = "You may get {E}{E}";
        String messageToPlay = "do nothing";
        boolean exiled = false;
        if (card != null) {
            controller.moveCardsToExile(card, source, game, true, null, "");
            if (game.getState().getZone(card.getId()) == Zone.EXILED) {
                mainMessage += ". If you don't, you may play " + card.getLogName() + " until end of turn.";
                messageToPlay = "you may play " + card.getName() + " until end of turn";
                exiled = true;
            }
        }

        boolean choice = controller.chooseUse(
                Outcome.Benefit, mainMessage, "",
                "get two energy counters", messageToPlay, source, game
        );

        if (choice) {
            new GetEnergyCountersControllerEffect(2)
                    .apply(game, source);
        } else if (exiled) {
            CardUtil.makeCardPlayable(
                    game, source, card, false, Duration.EndOfTurn, false
            );
        }
        return true;
    }
}