package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoShintaiOfHiddenCruelty extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(
            "creature with toughness less than or equal to the number of Shrines you control"
    );

    static {
        filter.add(GoShintaiOfHiddenCrueltyPredicate.instance);
    }

    public GoShintaiOfHiddenCruelty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // At the beginning of your end step, you may pay {1}. When you do, destroy target creature with toughness X or less, where X is the number of Shrines you control.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DestroyTargetEffect(), false, "destroy target creature " +
                "with toughness X or less, where X is the number of Shrines you control"
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DoWhenCostPaid(ability, new GenericManaCost(1), "Pay {1}?"),
                TargetController.YOU, false
        ).addHint(GoShintaiOfHiddenCrueltyPredicate.getHint()));
    }

    private GoShintaiOfHiddenCruelty(final GoShintaiOfHiddenCruelty card) {
        super(card);
    }

    @Override
    public GoShintaiOfHiddenCruelty copy() {
        return new GoShintaiOfHiddenCruelty(this);
    }
}

enum GoShintaiOfHiddenCrueltyPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SHRINE);
    private static final Hint hint = new ValueHint(
            "Shrines you control", new PermanentsOnBattlefieldCount(filter)
    );

    public static Hint getHint() {
        return hint;
    }

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return input.getObject().getToughness().getValue() <= game.getBattlefield().count(
                filter, input.getSourceId(), input.getPlayerId(), game
        );
    }
}
