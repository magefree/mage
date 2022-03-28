package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.CantBeTargetedAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterStackObject;
import mage.filter.StaticFilters;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TomikDistinguishedAdvokist extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject();

    static {
        filter.add(TargetedByOpponentsPredicate.instance);
    }

    public TomikDistinguishedAdvokist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lands on the battlefield and land cards in graveyards can't be the targets of spells or abilities your opponents control.
        Ability ability = new SimpleStaticAbility(new CantBeTargetedAllEffect(
                StaticFilters.FILTER_LANDS, filter, Duration.WhileOnBattlefield
        ).setText("lands on the battlefield"));
        ability.addEffect(new TomikDistinguishedAdvokistTargetEffect());
        this.addAbility(ability);

        // Your opponents can't play land cards from graveyards.
        this.addAbility(new SimpleStaticAbility(new TomikDistinguishedAdvokistRestrictionEffect()));
    }

    private TomikDistinguishedAdvokist(final TomikDistinguishedAdvokist card) {
        super(card);
    }

    @Override
    public TomikDistinguishedAdvokist copy() {
        return new TomikDistinguishedAdvokist(this);
    }
}

enum TargetedByOpponentsPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        StackObject stackObject = game.getStack().getStackObject(input.getObject().getId());
        Permanent source = input.getSource().getSourcePermanentOrLKI(game);
        if (stackObject != null && source != null) {
            Player controller = game.getPlayer(source.getControllerId());
            return controller != null && game.isOpponent(controller, stackObject.getControllerId());
        }
        return false;
    }

    @Override
    public String toString() {
        return "targeted spells or abilities your opponents control";
    }
}

class TomikDistinguishedAdvokistTargetEffect extends ContinuousRuleModifyingEffectImpl {

    TomikDistinguishedAdvokistTargetEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "and land cards in graveyards can't be the targets of spells or abilities your opponents control";
    }

    private TomikDistinguishedAdvokistTargetEffect(final TomikDistinguishedAdvokistTargetEffect effect) {
        super(effect);
    }

    @Override
    public TomikDistinguishedAdvokistTargetEffect copy() {
        return new TomikDistinguishedAdvokistTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card targetCard = game.getCard(event.getTargetId());
        StackObject stackObject = game.getStack().getStackObject(event.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        return targetCard != null && stackObject != null && player != null
                && player.hasOpponent(stackObject.getControllerId(), game)
                && game.getState().getZone(targetCard.getId()) == Zone.GRAVEYARD
                && targetCard.isLand(game);
    }
}

class TomikDistinguishedAdvokistRestrictionEffect extends ContinuousRuleModifyingEffectImpl {

    TomikDistinguishedAdvokistRestrictionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "Your opponents can't play land cards from graveyards";
    }

    private TomikDistinguishedAdvokistRestrictionEffect(final TomikDistinguishedAdvokistRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public TomikDistinguishedAdvokistRestrictionEffect copy() {
        return new TomikDistinguishedAdvokistRestrictionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.hasOpponent(event.getPlayerId(), game)
                && game.getState().getZone(event.getSourceId()) == Zone.GRAVEYARD;
    }
}
