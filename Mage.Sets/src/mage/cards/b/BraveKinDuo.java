package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BraveKinDuo extends CardImpl {

    public BraveKinDuo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.MOUSE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, {T}: Target creature gets +1/+1 until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new BoostTargetEffect(1, 1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BraveKinDuo(final BraveKinDuo card) {
        super(card);
    }

    @Override
    public BraveKinDuo copy() {
        return new BraveKinDuo(this);
    }
}
