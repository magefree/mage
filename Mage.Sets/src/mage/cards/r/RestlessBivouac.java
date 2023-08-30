package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RestlessBivouac extends CardImpl {

    public RestlessBivouac(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Restless Fortress enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R} or {W}.
        this.addAbility(new RedManaAbility());
        this.addAbility(new WhiteManaAbility());

        // {1}{R}{W}: Restless Bivouac becomes a 2/2 red and white Ox creature until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(2, 2, "2/2 red and white Ox creature")
                        .withColor("RW").withSubType(SubType.OX),
                CardType.LAND, Duration.EndOfTurn
        ), new ManaCostsImpl<>("{1}{R}{W}")));

        // Whenever Restless Bivouac attacks, put a +1/+1 counter on target creature you control.
        Ability ability = new AttacksTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private RestlessBivouac(final RestlessBivouac card) {
        super(card);
    }

    @Override
    public RestlessBivouac copy() {
        return new RestlessBivouac(this);
    }
}
