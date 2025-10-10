package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author TheElk801
 */
public final class MutagenToken extends TokenImpl {

    public MutagenToken() {
        super("Mutagen Token", "Mutagen token");
        cardType.add(CardType.ARTIFACT);
        subtype.add(SubType.MUTAGEN);

        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost().setText("sacrifice this token"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MutagenToken(final MutagenToken token) {
        super(token);
    }

    public MutagenToken copy() {
        return new MutagenToken(this);
    }
}
