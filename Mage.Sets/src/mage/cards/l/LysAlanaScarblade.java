package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class LysAlanaScarblade extends CardImpl {

    private static final FilterCard filter = new FilterCard("an Elf card");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    private static final DynamicValue xValue = new SignInversionDynamicValue(
            new PermanentsOnBattlefieldCount(new FilterControlledPermanent(SubType.ELF, "Elves you control"), null)
    );

    public LysAlanaScarblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ELF, SubType.ASSASSIN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}, Discard an Elf card: Target creature gets -X/-X until end of turn, where X is the number of Elves you control.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), new TapSourceCost());
        ability.addCost(new DiscardCardCost(filter));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private LysAlanaScarblade(final LysAlanaScarblade card) {
        super(card);
    }

    @Override
    public LysAlanaScarblade copy() {
        return new LysAlanaScarblade(this);
    }
}
