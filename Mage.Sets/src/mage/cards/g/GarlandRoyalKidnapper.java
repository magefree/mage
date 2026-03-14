package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.BecomesMonarchTargetEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GarlandRoyalKidnapper extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("creatures you control but don't own");

    static {
        filter.add(TargetController.NOT_YOU.getOwnerPredicate());
    }

    public GarlandRoyalKidnapper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // When Garland enters, target opponent becomes the monarch.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BecomesMonarchTargetEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability.addHint(MonarchHint.instance));

        // Whenever an opponent becomes the monarch, gain control of target creature that player controls for as long as they're the monarch.
        this.addAbility(new GarlandRoyalKidnapperTriggeredAbility());

        // Creatures you control but don't own get +2/+2 and can't be sacrificed.
        ability = new SimpleStaticAbility(new BoostAllEffect(
                2, 2, Duration.WhileOnBattlefield, filter, false
        ));
        ability.addEffect(new GarlandRoyalKidnapperSacrificeEffect());
        this.addAbility(ability);
    }

    private GarlandRoyalKidnapper(final GarlandRoyalKidnapper card) {
        super(card);
    }

    @Override
    public GarlandRoyalKidnapper copy() {
        return new GarlandRoyalKidnapper(this);
    }
}

class GarlandRoyalKidnapperTriggeredAbility extends TriggeredAbilityImpl {

    GarlandRoyalKidnapperTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GarlandRoyalKidnapperControlEffect());
        this.addTarget(new TargetOpponent());
        this.setTriggerPhrase("Whenever an opponent becomes the monarch, ");
    }

    private GarlandRoyalKidnapperTriggeredAbility(final GarlandRoyalKidnapperTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GarlandRoyalKidnapperTriggeredAbility copy() {
        return new GarlandRoyalKidnapperTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_MONARCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player player = game.getPlayer(event.getTargetId());
        if (player == null || !game.getOpponents(getControllerId()).contains(player.getId())) {
            return false;
        }
        FilterPermanent filter = new FilterCreaturePermanent("creature controlled by " + player.getName());
        filter.add(new ControllerIdPredicate(player.getId()));
        this.getTargets().clear();
        this.addTarget(new TargetPermanent(filter));
        this.getEffects().setValue("monarchId", player.getId());
        return true;
    }
}

class GarlandRoyalKidnapperControlEffect extends GainControlTargetEffect {

    GarlandRoyalKidnapperControlEffect() {
        super(Duration.Custom, true);
        staticText = "gain control of target creature that player controls for as long as they're the monarch";
    }

    private GarlandRoyalKidnapperControlEffect(final GarlandRoyalKidnapperControlEffect effect) {
        super(effect);
    }

    @Override
    public GarlandRoyalKidnapperControlEffect copy() {
        return new GarlandRoyalKidnapperControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent != null
                && Optional
                .ofNullable((UUID) getValue("monarchId"))
                .filter(uuid -> uuid.equals(game.getMonarchId()))
                .isPresent()) {
            return super.apply(game, source);
        }
        discard();
        return false;
    }
}

class GarlandRoyalKidnapperSacrificeEffect extends ContinuousEffectImpl {

    GarlandRoyalKidnapperSacrificeEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "and can't be sacrificed";
    }

    private GarlandRoyalKidnapperSacrificeEffect(final GarlandRoyalKidnapperSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public GarlandRoyalKidnapperSacrificeEffect copy() {
        return new GarlandRoyalKidnapperSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_CREATURE, source.getControllerId(), source, game
        )) {
            if (!permanent.isOwnedBy(source.getControllerId())) {
                permanent.setCanBeSacrificed(true);
            }
        }
        return true;
    }
}
