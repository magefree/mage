package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateOncePerGameActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FirstLittlePig extends CardImpl {

    public FirstLittlePig(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/W}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{G/W}: Exile target artifact or enchantment. Activate only once.
        Ability ability = new ActivateOncePerGameActivatedAbility(
            new ExileTargetEffect(),
            new ManaCostsImpl<>("{1}{G/W}")
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.addAbility(ability);
    }

    private FirstLittlePig(final FirstLittlePig card) {
        super(card);
    }

    @Override
    public FirstLittlePig copy() {
        return new FirstLittlePig(this);
    }
}
