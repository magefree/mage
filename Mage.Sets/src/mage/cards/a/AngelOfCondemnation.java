package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledToBattlefieldAbility;
import mage.abilities.costs.common.ExertSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class AngelOfCondemnation extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public AngelOfCondemnation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {2}{W}, {T}: Exile another target creature. Return that card to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(
                new AngelOfCondemnationExileUntilEOTEffect(), new ManaCostsImpl<>("{2}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // {2}{W}, {T}, Exert Angel of Condemnation: Exile another target creature until Angel of Condemnation leaves the battlefield.
        ability = new SimpleActivatedAbility(new ExileUntilSourceLeavesEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExertSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new OnLeaveReturnExiledToBattlefieldAbility()));
        this.addAbility(ability);
    }

    private AngelOfCondemnation(final AngelOfCondemnation card) {
        super(card);
    }

    @Override
    public AngelOfCondemnation copy() {
        return new AngelOfCondemnation(this);
    }
}

class AngelOfCondemnationExileUntilEOTEffect extends OneShotEffect {

    AngelOfCondemnationExileUntilEOTEffect() {
        super(Outcome.Detriment);
        staticText = "exile another target creature. Return that card to the battlefield " +
                "under its owner's control at the beginning of the next end step";
    }

    private AngelOfCondemnationExileUntilEOTEffect(final AngelOfCondemnationExileUntilEOTEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || permanent == null) {
            return false;
        }
        controller.moveCards(permanent, Zone.EXILED, source, game);
        //create delayed triggered ability
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false)
                        .setTargetPointer(new FixedTarget(source.getFirstTarget(), game))
        ), source);
        return true;
    }

    @Override
    public AngelOfCondemnationExileUntilEOTEffect copy() {
        return new AngelOfCondemnationExileUntilEOTEffect(this);
    }
}
