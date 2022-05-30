
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ElvishPathcutter extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Elf creature");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public ElvishPathcutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {2}{G}: Target Elf creature gains forestwalk until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(new ForestwalkAbility(), Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}"));
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private ElvishPathcutter(final ElvishPathcutter card) {
        super(card);
    }

    @Override
    public ElvishPathcutter copy() {
        return new ElvishPathcutter(this);
    }
}
