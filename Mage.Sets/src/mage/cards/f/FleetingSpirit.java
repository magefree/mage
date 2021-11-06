package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class FleetingSpirit extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards from your graveyard");

    public FleetingSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {W}, Exile three cards from your graveyard: Fleeting Spirit gains first strike until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn),
                new ManaCostsImpl<>("{W}")
        );
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(3, filter)));
        this.addAbility(ability);

        // Discard a card: Exile Fleeting Spirit. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.addAbility(new SimpleActivatedAbility(new FleetingSpiritEffect(), new DiscardCardCost()));
    }

    private FleetingSpirit(final FleetingSpirit card) {
        super(card);
    }

    @Override
    public FleetingSpirit copy() {
        return new FleetingSpirit(this);
    }
}

class FleetingSpiritEffect extends OneShotEffect {

    public FleetingSpiritEffect() {
        super(Outcome.Protect);
        staticText = "Exile {this}. Return it to the battlefield under its owner's control at the beginning of the next end step";
    }

    private FleetingSpiritEffect(final FleetingSpiritEffect effect) {
        super(effect);
    }

    @Override
    public FleetingSpiritEffect copy() {
        return new FleetingSpiritEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller == null || permanent == null) {
            return false;
        }
        int zcc = permanent.getZoneChangeCounter(game);
        if (!controller.moveCards(permanent, Zone.EXILED, source, game)) {
            return false;
        }
        Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
        effect.setTargetPointer(new FixedTarget(permanent.getId(), zcc + 1));
        AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}
