package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourceControllerCountersCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.RebelRedToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Grath
 */
public final class OtharriSunsGlory extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped Rebel you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(SubType.REBEL.getPredicate());
    }

    public OtharriSunsGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying,
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink,
        this.addAbility(LifelinkAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Otharri, Suns' Glory attacks, you get an experience counter. Then create a 2/2 red Rebel creature token that’s tapped and attacking for each experience counter you have.
        Ability ability = new AttacksTriggeredAbility(new AddCountersPlayersEffect(CounterType.EXPERIENCE.createInstance(), TargetController.YOU));
        ability.addEffect(new CreateTokenEffect(
                new RebelRedToken(),
                SourceControllerCountersCount.EXPERIENCE,
                true, true
        ).concatBy("Then"));
        this.addAbility(ability);

        // {2}{R}{W}, Tap an untapped Rebel you control: Return Otharri from your graveyard to the battlefield tapped.
        ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnToBattlefieldUnderOwnerControlSourceEffect(true, -1)
                        .setText("return {this} from your graveyard to the battlefield tapped"),
                new ManaCostsImpl<>("{2}{R}{W}"));
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(1, 1, filter, false)));
        this.addAbility(ability);
    }

    private OtharriSunsGlory(final OtharriSunsGlory card) {
        super(card);
    }

    @Override
    public OtharriSunsGlory copy() {
        return new OtharriSunsGlory(this);
    }
}