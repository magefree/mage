package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.CompoundAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.LoseAbilityAllEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheFireNationDrill extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public TheFireNationDrill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When The Fire Nation Drill enters, you may tap it. When you do, destroy target creature with power 4 or less.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TheFireNationDrillEffect()));

        // {1}: Permanents your opponents control lose hexproof and indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(new LoseAbilityAllEffect(
                new CompoundAbility(HexproofAbility.getInstance(), IndestructibleAbility.getInstance()), Duration.EndOfTurn, filter
        ).setText("permanents your opponents control lose hexproof and indestructible until end of turn."), new GenericManaCost(1)));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private TheFireNationDrill(final TheFireNationDrill card) {
        super(card);
    }

    @Override
    public TheFireNationDrill copy() {
        return new TheFireNationDrill(this);
    }
}

// custom effect needed as we can't use TapSourceCost with DoWhenCostPaid due to it not ignoring summoning sickness
class TheFireNationDrillEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with power 4 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 5));
    }

    TheFireNationDrillEffect() {
        super(Outcome.Benefit);
        staticText = "you may tap it. When you do, destroy target creature with power 4 or less";
    }

    private TheFireNationDrillEffect(final TheFireNationDrillEffect effect) {
        super(effect);
    }

    @Override
    public TheFireNationDrillEffect copy() {
        return new TheFireNationDrillEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null
                || permanent == null
                || permanent.isTapped()
                || !player.chooseUse(Outcome.Tap, "Tap " + permanent.getIdName() + '?', source, game)
                || !permanent.tap(source, game)) {
            return false;
        }
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
