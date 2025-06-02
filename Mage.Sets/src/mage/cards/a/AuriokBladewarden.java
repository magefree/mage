package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class AuriokBladewarden extends CardImpl {

    public AuriokBladewarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target creature gets +X/+X until end of turn, where X is Auriok Bladewarden's power.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(SourcePermanentPowerValue.NOT_NEGATIVE, SourcePermanentPowerValue.NOT_NEGATIVE, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AuriokBladewarden(final AuriokBladewarden card) {
        super(card);
    }

    @Override
    public AuriokBladewarden copy() {
        return new AuriokBladewarden(this);
    }
}
