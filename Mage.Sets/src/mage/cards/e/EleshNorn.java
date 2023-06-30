package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EleshNorn extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledCreaturePermanent("other creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public EleshNorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.PRAETOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);
        this.secondSideCardClazz = mage.cards.t.TheArgentEtchings.class;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever a source an opponent controls deals damage to you or a permanent you control, that source's controller loses 2 life unless they pay {1}.
        this.addAbility(new EleshNornTriggeredAbility());

        // {2}{W}, Sacrifice three other creatures: Exile Elesh Norn, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED),
                new ManaCostsImpl<>("{2}{W}")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(3, filter)));
        this.addAbility(ability);
    }

    private EleshNorn(final EleshNorn card) {
        super(card);
    }

    @Override
    public EleshNorn copy() {
        return new EleshNorn(this);
    }
}

class EleshNornTriggeredAbility extends TriggeredAbilityImpl {

    EleshNornTriggeredAbility() {
        super(Zone.BATTLEFIELD, new EleshNornEffect());
    }

    private EleshNornTriggeredAbility(final EleshNornTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EleshNornTriggeredAbility copy() {
        return new EleshNornTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        switch (event.getType()) {
            case DAMAGED_PLAYER:
            case DAMAGE_PERMANENT:
                return true;
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(getControllerId()).contains(game.getControllerId(event.getSourceId()))) {
            return false;
        }
        getEffects().setTargetPointer(new FixedTarget(game.getControllerId(event.getSourceId())));
        switch (event.getType()) {
            case DAMAGED_PLAYER:
                return isControlledBy(event.getTargetId());
            case DAMAGE_PERMANENT:
                return isControlledBy(game.getControllerId(event.getTargetId()));
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a source an opponent controls deals damage to you or a permanent you control, " +
                "that source's controller loses 2 life unless they pay {1}.";
    }
}

class EleshNornEffect extends OneShotEffect {

    EleshNornEffect() {
        super(Outcome.Benefit);
    }

    private EleshNornEffect(final EleshNornEffect effect) {
        super(effect);
    }

    @Override
    public EleshNornEffect copy() {
        return new EleshNornEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        Cost cost = new GenericManaCost(1);
        if (player != null && cost.canPay(source, source, player.getId(), game) && player.chooseUse(Outcome.PreventDamage, "Pay {1}?", source, game) && cost.pay(source, game, source, player.getId(), false)) {
            return false;
        }
        return player.loseLife(2, game, source, false) > 0;
    }
}
