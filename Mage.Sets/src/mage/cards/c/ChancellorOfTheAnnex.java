
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.ChancellorAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward
 */
public final class ChancellorOfTheAnnex extends CardImpl {

    public ChancellorOfTheAnnex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}{W}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // You may reveal this card from your opening hand. If you do, when each opponent casts their first spell of the game, counter that spell unless that player pays {1}.
        this.addAbility(new ChancellorAbility(new ChancellorOfTheAnnexEffect()));

        this.addAbility(FlyingAbility.getInstance());

        // Whenever an opponent casts a spell, counter it unless that player pays {1}.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new GenericManaCost(1)), StaticFilters.FILTER_SPELL, false, SetTargetPointer.SPELL));
    }

    private ChancellorOfTheAnnex(final ChancellorOfTheAnnex card) {
        super(card);
    }

    @Override
    public ChancellorOfTheAnnex copy() {
        return new ChancellorOfTheAnnex(this);
    }
}

class ChancellorOfTheAnnexEffect extends OneShotEffect {

    public ChancellorOfTheAnnexEffect() {
        super(Outcome.Benefit);
        staticText = "when each opponent casts their first spell of the game, counter that spell unless that player pays {1}";
    }

    private ChancellorOfTheAnnexEffect(final ChancellorOfTheAnnexEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            DelayedTriggeredAbility ability = new ChancellorOfTheAnnexDelayedTriggeredAbility(opponentId);
            game.addDelayedTriggeredAbility(ability, source);
        }
        return true;
    }

    @Override
    public ChancellorOfTheAnnexEffect copy() {
        return new ChancellorOfTheAnnexEffect(this);
    }

}

class ChancellorOfTheAnnexDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID playerId;

    ChancellorOfTheAnnexDelayedTriggeredAbility(UUID playerId) {
        super(new CounterUnlessPaysEffect(new GenericManaCost(1)));
        this.playerId = playerId;
    }

    private ChancellorOfTheAnnexDelayedTriggeredAbility(final ChancellorOfTheAnnexDelayedTriggeredAbility ability) {
        super(ability);
        this.playerId = ability.playerId;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(playerId)) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
            return true;
        }
        return false;
    }

    @Override
    public ChancellorOfTheAnnexDelayedTriggeredAbility copy() {
        return new ChancellorOfTheAnnexDelayedTriggeredAbility(this);
    }
}
