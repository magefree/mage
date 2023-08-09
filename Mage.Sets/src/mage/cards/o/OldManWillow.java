package mage.cards.o;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.PermanentToken;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OldManWillow extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another creature or a token");

    static {
        filter.add(OldManWillowPredicate.instance);
    }

    public OldManWillow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Old Man Willow's power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(LandsYouControlCount.instance)
        ));

        // Whenever Old Man Willow attacks, you may sacrifice another creature or a token. When you do, target creature an opponent controls gets -2/-2 until end of turn.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new BoostTargetEffect(-2, -2), false
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
                ability, new SacrificeTargetCost(filter),
                "Sacrifice another creature or a token?"
        )));
    }

    private OldManWillow(final OldManWillow card) {
        super(card);
    }

    @Override
    public OldManWillow copy() {
        return new OldManWillow(this);
    }
}

enum OldManWillowPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        if (input.getObject() instanceof PermanentToken) {
            return true;
        }
        return AnotherPredicate.instance.apply(input, game) && input.getObject().isCreature(game);
    }
}
