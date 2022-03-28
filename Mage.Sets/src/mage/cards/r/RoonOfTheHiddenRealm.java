package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class RoonOfTheHiddenRealm extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public RoonOfTheHiddenRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // {2}, {tap}: Exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RoonOfTheHiddenRealmEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

    }

    private RoonOfTheHiddenRealm(final RoonOfTheHiddenRealm card) {
        super(card);
    }

    @Override
    public RoonOfTheHiddenRealm copy() {
        return new RoonOfTheHiddenRealm(this);
    }
}

class RoonOfTheHiddenRealmEffect extends OneShotEffect {

    public RoonOfTheHiddenRealmEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step";
    }

    public RoonOfTheHiddenRealmEffect(final RoonOfTheHiddenRealmEffect effect) {
        super(effect);
    }

    @Override
    public RoonOfTheHiddenRealmEffect copy() {
        return new RoonOfTheHiddenRealmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            if (getTargetPointer().getFirst(game, source) != null) {
                Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
                if (permanent != null) {
                    int zcc = permanent.getZoneChangeCounter(game);
                    if (controller.moveCards(permanent, Zone.EXILED, source, game)) {
                        Effect effect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                        effect.setTargetPointer(new FixedTarget(permanent.getId(), zcc + 1));
                        AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility
                                = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
                        game.addDelayedTriggeredAbility(delayedAbility, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
