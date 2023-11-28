package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactCreaturePermanent;
import mage.game.permanent.token.DalekToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CultOfSkaro extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactCreaturePermanent("artifact creature you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public CultOfSkaro(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DALEK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Cult of Skaro attacks, choose one at random --
        // * Thay -- Put a +1/+1 counter on each artifact creature you control.
        Ability ability = new AttacksTriggeredAbility(
                new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter)
        ).withFirstModeFlavorWord("Thay");
        ability.getModes().setRandom(true);

        // * Caan -- Draw two cards.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(2)).withFlavorWord("Caan"));

        // * Sec -- Create a 3/3 black Dalek artifact creature token with menace.
        ability.addMode(new Mode(new CreateTokenEffect(new DalekToken())).withFlavorWord("Sec"));

        // * Jast -- Each opponent loses 4 life.
        ability.addMode(new Mode(new LoseLifeOpponentsEffect(4)).withFlavorWord("Jast"));

        this.addAbility(ability);
    }

    private CultOfSkaro(final CultOfSkaro card) {
        super(card);
    }

    @Override
    public CultOfSkaro copy() {
        return new CultOfSkaro(this);
    }
}
