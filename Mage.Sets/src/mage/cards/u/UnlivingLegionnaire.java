package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PowerUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class UnlivingLegionnaire extends CardImpl {

    public UnlivingLegionnaire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Power-up -- {5}{B}{B}: Return up to one target creature card from your graveyard to your hand. Put two +1/+1 counters on this creature.
        Ability ability = new PowerUpAbility(new ReturnFromGraveyardToHandTargetEffect(), new ManaCostsImpl<>("{5}{B}{B}"));
        ability.addTarget(new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)));
        this.addAbility(ability);
    }

    private UnlivingLegionnaire(final UnlivingLegionnaire card) {
        super(card);
    }

    @Override
    public UnlivingLegionnaire copy() {
        return new UnlivingLegionnaire(this);
    }
}
