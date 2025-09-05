package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeetleLegacyCriminal extends CardImpl {

    public BeetleLegacyCriminal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{U}, Exile this card from your graveyard: Put a +1/+1 counter on target creature. It gains flying until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{1}{U}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance())
                .setText("It gains flying until end of turn"));
        this.addAbility(ability);
    }

    private BeetleLegacyCriminal(final BeetleLegacyCriminal card) {
        super(card);
    }

    @Override
    public BeetleLegacyCriminal copy() {
        return new BeetleLegacyCriminal(this);
    }
}
