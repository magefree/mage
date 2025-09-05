package mage.cards.l;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LongFengGrandSecretariat extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("another creature you control or a land you control");

    static {
        filter.add(LongFengGrandSecretariatPredicate.instance);
    }

    public LongFengGrandSecretariat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/G}{B/G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever another creature you control or a land you control is put into a graveyard from the battlefield, put a +1/+1 counter on target creature you control.
        Ability ability = new PutIntoGraveFromBattlefieldAllTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                false, filter, false
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private LongFengGrandSecretariat(final LongFengGrandSecretariat card) {
        super(card);
    }

    @Override
    public LongFengGrandSecretariat copy() {
        return new LongFengGrandSecretariat(this);
    }
}

enum LongFengGrandSecretariatPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        return input.getObject().isLand(game)
                || input.getObject().isCreature(game)
                && AnotherPredicate.instance.apply(input, game);
    }
}
