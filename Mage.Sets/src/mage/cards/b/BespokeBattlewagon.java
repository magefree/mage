package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BespokeBattlewagon extends CardImpl {

    public BespokeBattlewagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // {T}: You get {E}{E}.
        this.addAbility(new SimpleActivatedAbility(new GetEnergyCountersControllerEffect(2), new TapSourceCost()));

        // {T}, Pay {E}{E}: Tap target creature.
        Ability ability = new SimpleActivatedAbility(new TapTargetEffect(), new TapSourceCost());
        ability.addCost(new PayEnergyCost(2));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {T}, Pay {E}{E}{E}: Draw a card.
        ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new TapSourceCost());
        ability.addCost(new PayEnergyCost(3));
        this.addAbility(ability);

        // Pay {E}{E}{E}{E}: Bespoke Battlewagon becomes an artifact creature until end of turn.
        this.addAbility(new SimpleActivatedAbility(new AddCardTypeSourceEffect(
                Duration.EndOfTurn, CardType.ARTIFACT, CardType.CREATURE
        ).setText("{this} becomes an artifact creature until end of turn"), new PayEnergyCost(4)));

        // Crew 4
        this.addAbility(new CrewAbility(4));
    }

    private BespokeBattlewagon(final BespokeBattlewagon card) {
        super(card);
    }

    @Override
    public BespokeBattlewagon copy() {
        return new BespokeBattlewagon(this);
    }
}
