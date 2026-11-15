package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class SpiderManToTheRescue extends CardImpl {

    public SpiderManToTheRescue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // No One Dies! -- When Spider-Man enters, you may tap him. When you do, another target nonattacking creature you control gains indestructible until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SpiderManToTheRescueEffect())
            .withFlavorWord("No One Dies!"));
    }

    private SpiderManToTheRescue(final SpiderManToTheRescue card) {
        super(card);
    }

    @Override
    public SpiderManToTheRescue copy() {
        return new SpiderManToTheRescue(this);
    }
}

class SpiderManToTheRescueEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter
            = new FilterControlledCreaturePermanent("another target nonattacking creature you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.not(AttackingPredicate.instance));
    }

    SpiderManToTheRescueEffect() {
        super(Outcome.Benefit);
        staticText = "you may tap him. When you do, another target nonattacking creature you control gains indestructible until end of turn";
    }

    private SpiderManToTheRescueEffect(final SpiderManToTheRescueEffect effect) {
        super(effect);
    }

    @Override
    public SpiderManToTheRescueEffect copy() {
        return new SpiderManToTheRescueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null || permanent.isTapped()
                || !player.chooseUse(Outcome.Tap, "Tap " + permanent.getIdName() + '?', source, game)
                || !permanent.tap(source, game)) {
            return false;
        }

        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
            new GainAbilityTargetEffect(IndestructibleAbility.getInstance()), false
        );
        ability.addTarget(new TargetPermanent(filter));
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
