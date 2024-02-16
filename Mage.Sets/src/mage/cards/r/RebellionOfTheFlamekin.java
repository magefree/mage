package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElementalShamanToken;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTargets;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Styxo
 */
public final class RebellionOfTheFlamekin extends CardImpl {

    public RebellionOfTheFlamekin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.ENCHANTMENT}, "{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        // Whenever you clash, you may pay {1}. If you do create a 3/1 Red Elemental Shaman creature token in play. If you won that token gains haste
        this.addAbility(new RebellionOfTheFlamekinTriggeredAbility());
    }

    private RebellionOfTheFlamekin(final RebellionOfTheFlamekin card) {
        super(card);
    }

    @Override
    public RebellionOfTheFlamekin copy() {
        return new RebellionOfTheFlamekin(this);
    }
}

class RebellionOfTheFlamekinTriggeredAbility extends TriggeredAbilityImpl {

    RebellionOfTheFlamekinTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new RebellionOfTheFlamekinEffect(), new GenericManaCost(1)));
    }

    private RebellionOfTheFlamekinTriggeredAbility(final RebellionOfTheFlamekinTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RebellionOfTheFlamekinTriggeredAbility copy() {
        return new RebellionOfTheFlamekinTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CLASHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        this.getEffects().setValue("clash", event.getFlag());
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever you clash, you may pay {1}. If you do, " +
                "create a 3/1 red Elemental Shaman creature token. " +
                "If you won, that token gains haste until end of turn. " +
                "<i>(This ability triggers after the clash ends.)</i>";
    }
}

class RebellionOfTheFlamekinEffect extends OneShotEffect {

    RebellionOfTheFlamekinEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    private RebellionOfTheFlamekinEffect(final RebellionOfTheFlamekinEffect effect) {
        super(effect);
    }

    @Override
    public RebellionOfTheFlamekinEffect copy() {
        return new RebellionOfTheFlamekinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new ElementalShamanToken();
        token.putOntoBattlefield(1, game, source, source.getControllerId());

        List<Permanent> permanents = token
                .getLastAddedTokenIds()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (!permanents.isEmpty() && (boolean) this.getValue("clash")) {
            game.addEffect(new GainAbilityTargetEffect(
                    HasteAbility.getInstance(), Duration.EndOfTurn
            ).setTargetPointer(new FixedTargets(permanents, game)), source);
        }
        return true;
    }
}
