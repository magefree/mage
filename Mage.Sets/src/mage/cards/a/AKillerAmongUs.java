package mage.cards.a;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.HumanToken;
import mage.game.permanent.token.MerfolkToken;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 * A Killer Among Us {4}{G}
 * Enchantment
 * When A Killer Among Us enters the battlefield, create a 1/1 white Human creature token, a 1/1 blue Merfolk creature token, and a 1/1 red Goblin creature token. Then secretly choose Human, Merfolk, or Goblin.
 * Sacrifice A Killer Among Us, Reveal the chosen creature type: If target attacking creature token is the chosen type, put three +1/+1 counters on it and it gains deathtouch until end of turn.
 *   ┌─────┐
 * ┌─┤  ┌──┴┐
 * │ │  └──┬┘
 * └─┤ ┌─┐ │
 *   └─┘ └─┘
 *
 * @author DominionSpy
 */
public final class AKillerAmongUs extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public AKillerAmongUs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // When A Killer Among Us enters the battlefield, create a 1/1 white Human creature token, a 1/1 blue Merfolk creature token, and a 1/1 red Goblin creature token. Then secretly choose Human, Merfolk, or Goblin.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanToken()));
        ability.addEffect(new CreateTokenEffect(new MerfolkToken())
                .setText(", a 1/1 blue Merfolk creature token"));
        ability.addEffect(new CreateTokenEffect(new GoblinToken())
                .setText(", and a 1/1 red Goblin creature token."));
        ability.addEffect(new ChooseHumanMerfolkOrGoblinEffect());
        this.addAbility(ability);

        // Sacrifice A Killer Among Us, Reveal the chosen creature type: If target attacking creature token is the chosen type, put three +1/+1 counters on it and it gains deathtouch until end of turn.
        ability = new SimpleActivatedAbility(new AKillerAmongUsEffect(), new SacrificeSourceCost());
        ability.addCost(new AKillerAmongUsCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private AKillerAmongUs(final AKillerAmongUs card) {
        super(card);
    }

    @Override
    public AKillerAmongUs copy() {
        return new AKillerAmongUs(this);
    }
}

class ChooseHumanMerfolkOrGoblinEffect extends OneShotEffect {

    public static final String SECRET_CREATURE_TYPE = "_susCreatureType";
    public static final String SECRET_OWNER = "_secOwn";

    ChooseHumanMerfolkOrGoblinEffect() {
        super(Outcome.Neutral);
        staticText = "Then secretly choose Human, Merfolk, or Goblin.";
    }

    private ChooseHumanMerfolkOrGoblinEffect(final ChooseHumanMerfolkOrGoblinEffect effect) {
        super(effect);
    }

    @Override
    public ChooseHumanMerfolkOrGoblinEffect copy() {
        return new ChooseHumanMerfolkOrGoblinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            return false;
        }

        Choice choice = new ChoiceImpl();
        Set<String> choices = new LinkedHashSet<>();
        choices.add("Human");
        choices.add("Merfolk");
        choices.add("Goblin");
        choice.setChoices(choices);
        choice.setMessage("Choose Human, Merfolk, or Goblin");

        controller.choose(outcome, choice, game);
        game.informPlayers(permanent.getName() + ": " + controller.getLogName() + " has secretly chosen a creature type.");

        SubType chosenType = SubType.fromString(choice.getChoice());
        setSecretCreatureType(chosenType, source, game);
        setSecretOwner(source.getControllerId(), source, game);
        return true;
    }

    public static void setSecretCreatureType(SubType type, Ability source, Game game) {
        String uniqueRef = getUniqueReference(source, game);
        if (uniqueRef != null) {
            game.getState().setValue(uniqueRef + SECRET_CREATURE_TYPE, type);
        }
    }

    public static SubType getSecretCreatureType(Ability source, Game game) {
        String uniqueRef = getUniqueReference(source, game);
        if (uniqueRef != null) {
            return (SubType) game.getState().getValue(uniqueRef +
                    ChooseHumanMerfolkOrGoblinEffect.SECRET_CREATURE_TYPE);
        }
        return null;
    }

    public static void setSecretOwner(UUID owner, Ability source, Game game) {
        String uniqueRef = getUniqueReference(source, game);
        if (uniqueRef != null) {
            game.getState().setValue(getUniqueReference(source, game) + SECRET_OWNER, owner);
        }
    }

    public static UUID getSecretOwner(Ability source, Game game) {
        String uniqueRef = getUniqueReference(source, game);
        if (uniqueRef != null) {
            return (UUID) game.getState().getValue(getUniqueReference(source, game) +
                    ChooseHumanMerfolkOrGoblinEffect.SECRET_OWNER);
        }
        return null;
    }

    private static String getUniqueReference(Ability source, Game game) {
        if (game.getPermanentOrLKIBattlefield(source.getSourceId()) != null) {
            return source.getSourceId() + "_" + (game.getPermanentOrLKIBattlefield(source.getSourceId()).getZoneChangeCounter(game));
        }
        return null;
    }
}

class AKillerAmongUsEffect extends OneShotEffect {

    AKillerAmongUsEffect() {
        super(Outcome.Benefit);
        this.staticText = "If target attacking creature token is the chosen type, " +
                "put three +1/+1 counters on it and it gains deathtouch until end of turn.";
    }

    private AKillerAmongUsEffect(final AKillerAmongUsEffect effect) {
        super(effect);
    }

    @Override
    public AKillerAmongUsEffect copy() {
        return new AKillerAmongUsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null) {
            return false;
        }
        SubType creatureType = ChooseHumanMerfolkOrGoblinEffect.getSecretCreatureType(source, game);
        if (creatureType != null && creature.getSubtype().contains(creatureType)) {
            creature.addCounters(CounterType.P1P1.createInstance(3), source, game);
            game.addEffect(new GainAbilityTargetEffect(
                    DeathtouchAbility.getInstance(), Duration.EndOfTurn
            ), source);
        }
        return true;
    }
}

class AKillerAmongUsCost extends CostImpl {

    AKillerAmongUsCost() {
        this.text = "Reveal the creature type you chose";
    }

    private AKillerAmongUsCost(final AKillerAmongUsCost cost) {
        super(cost);
    }

    @Override
    public AKillerAmongUsCost copy() {
        return new AKillerAmongUsCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return controllerId != null
                && controllerId.equals(ChooseHumanMerfolkOrGoblinEffect.getSecretOwner(source, game))
                && ChooseHumanMerfolkOrGoblinEffect.getSecretCreatureType(source, game) != null;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (controllerId == null || !controllerId.equals(ChooseHumanMerfolkOrGoblinEffect.getSecretOwner(source, game))) {
            return false;
        }
        SubType creatureType = ChooseHumanMerfolkOrGoblinEffect.getSecretCreatureType(source, game);
        if (creatureType == null) {
            return paid;
        }

        Player controller = game.getPlayer(controllerId);
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() +
                    " reveals the secretly chosen creature type " + creatureType);
        }

        paid = true;
        return paid;
    }
}
