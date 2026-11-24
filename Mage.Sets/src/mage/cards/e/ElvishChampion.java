
package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostGainAbilityGenericEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.targetpointer.FilterAllPermanentsTargetPointer;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class ElvishChampion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other Elf creatures");
    static {
        filter.add(SubType.ELF.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public ElvishChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Elf creatures get +1/+1 and have forestwalk. (They can't be blocked as long as defending player controls a Forest.)
        this.addAbility(new SimpleStaticAbility(new BoostGainAbilityGenericEffect(
                1, 1, Duration.WhileOnBattlefield, new ForestwalkAbility()
        ).setTargetPointer(new FilterAllPermanentsTargetPointer(filter))));
    }

    private ElvishChampion(final ElvishChampion card) {
        super(card);
    }

    @Override
    public ElvishChampion copy() {
        return new ElvishChampion(this);
    }
}
