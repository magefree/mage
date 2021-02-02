package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.RepairAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author NinthWorld
 */
public final class AstromechDroid extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("starship creature");

    static {
        filter.add(SubType.STARSHIP.getPredicate());
    }

    public AstromechDroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{W}");
        
        this.subtype.add(SubType.DROID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {T}: Target starship creature you control gets +1/+1 and gains vigilance until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(1, 1, Duration.EndOfTurn)
                    .setText("Target starship creature you control gets +1/+1"),
                new TapSourceCost());
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfTurn)
                    .setText("and gains vigilance until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Repair 4
        this.addAbility(new RepairAbility(4));
    }

    private AstromechDroid(final AstromechDroid card) {
        super(card);
    }

    @Override
    public AstromechDroid copy() {
        return new AstromechDroid(this);
    }
}
