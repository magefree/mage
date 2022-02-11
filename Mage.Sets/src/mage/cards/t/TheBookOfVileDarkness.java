package mage.cards.t;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.VecnaToken;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBookOfVileDarkness extends CardImpl {

    public TheBookOfVileDarkness(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}{B}{B}");

        this.addSuperType(SuperType.LEGENDARY);

        // At the beginning of your end step, if you lost 2 or more life this turn, create a 2/2 black Zombie creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new CreateTokenEffect(new ZombieToken()), TargetController.YOU,
                TheBookOfVileDarknessCondition.instance, false
        ));

        // {T}, Exile The Book of Vile Darkness and artifacts you control named Eye of Vecna and Hand of Vecna: Create Vecna, a legendary 8/8 black Zombie God creature token with indestructible and all triggered abilities of the exiled cards.
        Ability ability = new SimpleActivatedAbility(new TheBookOfVileDarknessEffect(), new TapSourceCost());
        ability.addCost(new TheBookOfVileDarknessCost());
        this.addAbility(ability);
    }

    private TheBookOfVileDarkness(final TheBookOfVileDarkness card) {
        super(card);
    }

    @Override
    public TheBookOfVileDarkness copy() {
        return new TheBookOfVileDarkness(this);
    }
}

enum TheBookOfVileDarknessCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        return watcher != null && watcher.getLifeLost(source.getControllerId()) >= 2;
    }

    @Override
    public String toString() {
        return "if you lost 2 or more life this turn";
    }
}

class TheBookOfVileDarknessCost extends CostImpl {

    private static final FilterPermanent filter1
            = new FilterControlledArtifactPermanent("artifact you control named Eye of Vecna");
    private static final FilterPermanent filter2
            = new FilterControlledArtifactPermanent("artifact you control named Hand of Vecna");

    static {
        filter1.add(new NamePredicate("Eye of Vecna"));
        filter2.add(new NamePredicate("Hand of Vecna"));
    }

    public TheBookOfVileDarknessCost() {
        this.text = "exile {this} and artifacts you control named Eye of Vecna and Hand of Vecna";
    }

    public TheBookOfVileDarknessCost(final TheBookOfVileDarknessCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Player controller = game.getPlayer(controllerId);
        Permanent sourcePermanent = ability.getSourcePermanentIfItStillExists(game);
        if (controller == null || sourcePermanent == null) {
            return paid;
        }
        Permanent eye = getPermanent(filter1, controller, ability, game);
        if (eye == null) {
            return paid;
        }
        Permanent hand = getPermanent(filter2, controller, ability, game);
        if (hand == null) {
            return paid;
        }
        controller.moveCards(new CardsImpl(Arrays.asList(sourcePermanent, eye, hand)), Zone.EXILED, source, game);
        Set<MageObjectReference> morSet = new HashSet<>();
        morSet.add(new MageObjectReference(sourcePermanent, game));
        morSet.add(new MageObjectReference(eye, game));
        morSet.add(new MageObjectReference(hand, game));
        ability.getEffects().setValue("BookEyeHand", morSet);
        paid = true;
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return source.getSourcePermanentIfItStillExists(game) != null
                && game.getBattlefield().count(filter1, source.getSourceId(), source.getControllerId(), game) > 0
                && game.getBattlefield().count(filter2, source.getSourceId(), source.getControllerId(), game) > 0;
    }

    private static Permanent getPermanent(FilterPermanent filter, Player controller, Ability source, Game game) {
        int count = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
        switch (count) {
            case 0:
                return null;
            case 1:
                return game.getBattlefield().getActivePermanents(
                        filter, source.getControllerId(), source.getSourceId(), game
                ).stream().findFirst().orElse(null);
            default:
                break;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.setNotTarget(true);
        controller.choose(Outcome.Sacrifice, target, source.getControllerId(), game);
        return game.getPermanent(target.getFirstTarget());
    }

    @Override
    public TheBookOfVileDarknessCost copy() {
        return new TheBookOfVileDarknessCost(this);
    }
}

class TheBookOfVileDarknessEffect extends OneShotEffect {

    TheBookOfVileDarknessEffect() {
        super(Outcome.Benefit);
        staticText = "create Vecna, a legendary 8/8 black Zombie God creature token "
                + "with indestructible and all triggered abilities of the exiled cards";
    }

    private TheBookOfVileDarknessEffect(final TheBookOfVileDarknessEffect effect) {
        super(effect);
    }

    @Override
    public TheBookOfVileDarknessEffect copy() {
        return new TheBookOfVileDarknessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<MageObjectReference> morSet = (Set<MageObjectReference>) getValue("BookEyeHand");
        if (morSet == null) {
            return false;
        }
        Token token = new VecnaToken();
        for (MageObjectReference mor : morSet) {
            // the card object in the mor doesn't work, so the permanent object is used
            Permanent card = mor.getPermanentOrLKIBattlefield(game);
            if (card == null) {
                continue;
            }
            for (Ability ability : card.getAbilities(game)) {
                if (ability instanceof TriggeredAbility) {
                    Ability copyAbility = ability.copy();
                    copyAbility.newId();
                    copyAbility.setControllerId(source.getControllerId());
                    token.addAbility(copyAbility);
                }
            }
        }
        return token.putOntoBattlefield(1, game, source, source.getControllerId());
    }
}
