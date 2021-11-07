package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
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
public final class TravelingMinister extends CardImpl {

    public TravelingMinister(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Target creature gets +1/+0 until end of turn. You gain 1 life. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new BoostTargetEffect(1, 0), new TapSourceCost()
        );
        ability.addEffect(new GainLifeEffect(1));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TravelingMinister(final TravelingMinister card) {
        super(card);
    }

    @Override
    public TravelingMinister copy() {
        return new TravelingMinister(this);
    }
}
