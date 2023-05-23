package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.GoldForgeGarrisonGolemToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GoldenGuardian extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public GoldenGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.GOLEM}, "{4}",
                "Gold-Forge Garrison",
                new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );
        this.getLeftHalfCard().setPT(4, 4);

        // Defender
        this.getLeftHalfCard().addAbility(DefenderAbility.getInstance());

        // {2}: Golden Guardian fights another target creature you control. When Golden Guardian dies this turn, return it to the battlefield transformed under your control.
        Ability ability = new SimpleActivatedAbility(new FightTargetSourceEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new GoldenGuardianDelayedTriggeredAbility(), false
        ));
        this.getLeftHalfCard().addAbility(ability);

        // Gold-Forge Garrison
        // <i>(Transforms from Golden Guardian.)</i>
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                new InfoEffect("<i>(Transforms from Golden Guardian.)</i>")
        ));

        // {T}: Add two mana of any one color.
        this.getRightHalfCard().addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost()
        ));

        // {4}, {T}: Create a 4/4 colorless Golem artifact creature token.
        ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new GoldForgeGarrisonGolemToken(), 1), new GenericManaCost(4)
        );
        ability.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability);
    }

    private GoldenGuardian(final GoldenGuardian card) {
        super(card);
    }

    @Override
    public GoldenGuardian copy() {
        return new GoldenGuardian(this);
    }
}

class GoldenGuardianDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public GoldenGuardianDelayedTriggeredAbility() {
        super(new GoldenGuardianReturnTransformedEffect(), Duration.EndOfTurn);
        setTriggerPhrase("When {this} dies this turn, ");
    }

    public GoldenGuardianDelayedTriggeredAbility(final GoldenGuardianDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GoldenGuardianDelayedTriggeredAbility copy() {
        return new GoldenGuardianDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return ((ZoneChangeEvent) event).isDiesEvent() && event.getTargetId().equals(getSourceId());
    }
}

class GoldenGuardianReturnTransformedEffect extends OneShotEffect {

    public GoldenGuardianReturnTransformedEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "return it to the battlefield transformed under your control";
    }

    public GoldenGuardianReturnTransformedEffect(final GoldenGuardianReturnTransformedEffect effect) {
        super(effect);
    }

    @Override
    public GoldenGuardianReturnTransformedEffect copy() {
        return new GoldenGuardianReturnTransformedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return true;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + source.getSourceId(), Boolean.TRUE);
        Card card = game.getCard(source.getSourceId());
        if (card != null) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}
