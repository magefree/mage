package mage.cards.h;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author Merlingilb
 */
public class HiddenBase extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Starship creature");

    static {
        filter.add(SubType.STARSHIP.getPredicate());
    }

    public HiddenBase(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        //{T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        //{T}: Target starship creature gains haste until end of turn.
        SimpleActivatedAbility simpleActivatedAbility = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), new TapSourceCost());
        simpleActivatedAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(simpleActivatedAbility);
    }

    public HiddenBase(HiddenBase card) {
        super(card);
    }

    @Override
    public HiddenBase copy() {
        return new HiddenBase(this);
    }
}
