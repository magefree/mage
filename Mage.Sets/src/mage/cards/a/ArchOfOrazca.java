package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ArchOfOrazca extends CardImpl {

    public ArchOfOrazca(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Ascend
        this.addAbility(new AscendAbility());

        // {t}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {5}, {T}: Draw a card. Activate this ability only if you have the city's blessing.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new GenericManaCost(5),
                CitysBlessingCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addHint(CitysBlessingHint.instance);
        this.addAbility(ability);
    }

    private ArchOfOrazca(final ArchOfOrazca card) {
        super(card);
    }

    @Override
    public ArchOfOrazca copy() {
        return new ArchOfOrazca(this);
    }
}
