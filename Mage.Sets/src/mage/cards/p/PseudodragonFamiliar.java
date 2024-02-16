package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PseudodragonFamiliar extends CardImpl {

    public PseudodragonFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{U}: Target creature gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(FlyingAbility.getInstance()), new ManaCostsImpl<>("{2}{U}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private PseudodragonFamiliar(final PseudodragonFamiliar card) {
        super(card);
    }

    @Override
    public PseudodragonFamiliar copy() {
        return new PseudodragonFamiliar(this);
    }
}
