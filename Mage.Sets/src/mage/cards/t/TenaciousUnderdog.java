package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.keyword.BlitzAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TenaciousUnderdog extends CardImpl {

    public TenaciousUnderdog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Blitz—{2}{B}{B}, Pay 2 life.
        Ability ability = new BlitzAbility(this, "{2}{B}{B}");
        ability.addCost(new PayLifeCost(2));
        this.addAbility(ability);

        // You may cast Tenacious Underdog from your graveyard using its blitz ability.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new TenaciousUnderdogEffect()));
    }

    private TenaciousUnderdog(final TenaciousUnderdog card) {
        super(card);
    }

    @Override
    public TenaciousUnderdog copy() {
        return new TenaciousUnderdog(this);
    }
}

class TenaciousUnderdogEffect extends AsThoughEffectImpl {

    TenaciousUnderdogEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        staticText = "You may cast {this} from your graveyard using its blitz ability";
    }

    private TenaciousUnderdogEffect(final TenaciousUnderdogEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TenaciousUnderdogEffect copy() {
        return new TenaciousUnderdogEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        return objectId.equals(source.getSourceId())
                && source.isControlledBy(playerId)
                && affectedAbility instanceof BlitzAbility
                && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD
                && game.getCard(source.getSourceId()) != null;
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }
}
