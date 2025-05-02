package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
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
public final class SandstormVerge extends CardImpl {

    public SandstormVerge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {3}, {T}: Target creature can't block this turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new CantBlockTargetEffect(Duration.EndOfTurn), new GenericManaCost(3)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SandstormVerge(final SandstormVerge card) {
        super(card);
    }

    @Override
    public SandstormVerge copy() {
        return new SandstormVerge(this);
    }
}
