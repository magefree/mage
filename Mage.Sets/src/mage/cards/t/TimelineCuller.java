package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.WarpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TimelineCuller extends CardImpl {

    public TimelineCuller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");

        this.subtype.add(SubType.DRIX);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // You may cast this card from your graveyard using its warp ability.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new TimelineCullerEffect()));

        // Warp--{B}, Pay 2 life.
        Ability ability = new WarpAbility(this, "{B}", true);
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);
    }

    private TimelineCuller(final TimelineCuller card) {
        super(card);
    }

    @Override
    public TimelineCuller copy() {
        return new TimelineCuller(this);
    }
}

class TimelineCullerEffect extends AsThoughEffectImpl {

    TimelineCullerEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        staticText = "you may cast this card from your graveyard using its warp ability";
    }

    private TimelineCullerEffect(final TimelineCullerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TimelineCullerEffect copy() {
        return new TimelineCullerEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        return objectId.equals(source.getSourceId())
                && source.isControlledBy(playerId)
                && affectedAbility instanceof WarpAbility
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                && game.getCard(source.getSourceId()) != null;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }
}
