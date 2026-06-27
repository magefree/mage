package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SifsSpearmaster extends CardImpl {

    public SifsSpearmaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {T}: This creature deals damage equal to its power to target opponent.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE).setText("{this} deals damage equal to its power to target opponent"), new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SifsSpearmaster(final SifsSpearmaster card) {
        super(card);
    }

    @Override
    public SifsSpearmaster copy() {
        return new SifsSpearmaster(this);
    }
}
