package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HallOfHeliodsGenerosity extends CardImpl {

    private static final FilterCard filter = new FilterEnchantmentCard("enchantment card from your graveyard");

    public HallOfHeliodsGenerosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.addSuperType(SuperType.LEGENDARY);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}{W}, {T}: Put target enchantment card from your graveyard on top of your library.
        Ability ability = new SimpleActivatedAbility(
                new PutOnLibraryTargetEffect(true), new ManaCostsImpl<>("{1}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private HallOfHeliodsGenerosity(final HallOfHeliodsGenerosity card) {
        super(card);
    }

    @Override
    public HallOfHeliodsGenerosity copy() {
        return new HallOfHeliodsGenerosity(this);
    }
}
