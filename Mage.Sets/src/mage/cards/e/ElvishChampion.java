
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ForestwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class ElvishChampion extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Elf creatures");
    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public ElvishChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.ELF);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other Elf creatures get +1/+1 and have forestwalk. (They can't be blocked as long as defending player controls a Forest.)
        Ability ability = new SimpleStaticAbility(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, true));
        ability.addEffect(new GainAbilityAllEffect(new ForestwalkAbility(), Duration.WhileOnBattlefield, filter, true)
                .setText("and have forestwalk"));
        this.addAbility(ability);
    }

    private ElvishChampion(final ElvishChampion card) {
        super(card);
    }

    @Override
    public ElvishChampion copy() {
        return new ElvishChampion(this);
    }
}
