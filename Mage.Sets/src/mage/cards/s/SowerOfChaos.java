package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
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
public final class SowerOfChaos extends CardImpl {

    public SowerOfChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {2}{R}: Target creature can't block this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBlockTargetEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SowerOfChaos(final SowerOfChaos card) {
        super(card);
    }

    @Override
    public SowerOfChaos copy() {
        return new SowerOfChaos(this);
    }
}
