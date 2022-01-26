package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.RatToken;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PiperOfTheSwarm extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent(SubType.RAT, "Rats");
    private static final FilterControlledPermanent filter2 = new FilterControlledPermanent(SubType.RAT, "Rats");

    public PiperOfTheSwarm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Rats you control have menace.
        this.addAbility(new SimpleStaticAbility(
                new GainAbilityControlledEffect(new MenaceAbility(false), Duration.WhileOnBattlefield, filter)
        ));

        // {1}{B}, {T}: Create a 1/1 black Rat creature token.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new RatToken()), new ManaCostsImpl("{1}{B}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {2}{B}{B}, {T}, Sacrifice three Rats: Gain control of target creature.
        ability = new SimpleActivatedAbility(
                new GainControlTargetEffect(Duration.Custom), new ManaCostsImpl("{2}{B}{B}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(3, filter2)));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PiperOfTheSwarm(final PiperOfTheSwarm card) {
        super(card);
    }

    @Override
    public PiperOfTheSwarm copy() {
        return new PiperOfTheSwarm(this);
    }
}
