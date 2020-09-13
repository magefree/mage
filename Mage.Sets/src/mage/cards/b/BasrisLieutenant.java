package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterObject;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.permanent.token.KnightToken;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author htrajan
 */
public final class BasrisLieutenant extends CardImpl {

    private static final FilterObject<MageObject> multicoloredFilter = new FilterObject<>("multicolored");
    private static final FilterCreaturePermanent controlledCreatureWithP1P1CounterFilter = new FilterCreaturePermanent("creature you control");

    static {
        multicoloredFilter.add(MulticoloredPredicate.instance);
        controlledCreatureWithP1P1CounterFilter.add(TargetController.YOU.getControllerPredicate());
        controlledCreatureWithP1P1CounterFilter.add(CounterType.P1P1.getPredicate());
    }

    public BasrisLieutenant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance, protection from multicolored
        this.addAbility(VigilanceAbility.getInstance());

        // protection from multicolored
        this.addAbility(new ProtectionAbility(multicoloredFilter));

        // When Basri's Lieutenant enters the battlefield, put a +1/+1 counter on target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Whenever Basri's Lieutenant or another creature you control dies, if it had a +1/+1 counter on it, create a 2/2 white Knight creature token with vigilance.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(
                new CreateTokenEffect(new KnightToken()).setText("if it had a +1/+1 counter on it, create a 2/2 white Knight creature token with vigilance"),
                false,
                controlledCreatureWithP1P1CounterFilter
        ).setApplyFilterOnSource(true));
    }

    private BasrisLieutenant(final BasrisLieutenant card) {
        super(card);
    }

    @Override
    public BasrisLieutenant copy() {
        return new BasrisLieutenant(this);
    }
}
