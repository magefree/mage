package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.cost.CostModificationSourceEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class SanctumOfTranquilLight extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Shrine you control");
    private static final PermanentsOnBattlefieldCount count = new PermanentsOnBattlefieldCount(filter);

    static {
        filter.add(SubType.SHRINE.getPredicate());
    }

    public SanctumOfTranquilLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);

        // {5}{W}: Tap target creature. This ability costs {1} less to activate for each Shrine you control.
        Ability ability = new SimpleActivatedAbility(new SanctumOfTranquilLightActivatedAbility());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new CostModificationSourceEffect(Duration.EndOfGame, Outcome.Benefit, SanctumOfTranquilLightActivatedAbility.class, count, true).setText(""))
                .addHint(new ValueHint("Shrines you control", count)));
    }

    private SanctumOfTranquilLight(final SanctumOfTranquilLight card) {
        super(card);
    }

    @Override
    public SanctumOfTranquilLight copy() {
        return new SanctumOfTranquilLight(this);
    }
}

class SanctumOfTranquilLightActivatedAbility extends SimpleActivatedAbility {

    SanctumOfTranquilLightActivatedAbility() {
        super(new TapTargetEffect().setText("target creature. This ability costs {1} less to activate for each Shrine you control"), new ManaCostsImpl<>("{5}{W}"));
    }

    private SanctumOfTranquilLightActivatedAbility(SanctumOfTranquilLightActivatedAbility ability) {
        super(ability);
    }

    @Override
    public SanctumOfTranquilLightActivatedAbility copy() {
        return new SanctumOfTranquilLightActivatedAbility(this);
    }
}