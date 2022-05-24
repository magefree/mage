package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
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
public final class KeenGlidemaster extends CardImpl {

    public KeenGlidemaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {2}{U}: Target creature gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{2}{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KeenGlidemaster(final KeenGlidemaster card) {
        super(card);
    }

    @Override
    public KeenGlidemaster copy() {
        return new KeenGlidemaster(this);
    }
}
