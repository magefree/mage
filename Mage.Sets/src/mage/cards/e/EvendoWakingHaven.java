package mage.cards.e;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EvendoWakingHaven extends CardImpl {

    public EvendoWakingHaven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLANET);

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());

        // Station
        this.addAbility(new StationAbility());

        // STATION 12+
        // {G}, {T}: Add {G} for each creature you control.
        Ability ability = new DynamicManaAbility(
                Mana.GreenMana(1), CreaturesYouControlCount.SINGULAR, new ManaCostsImpl<>("{G}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(new StationLevelAbility(12).withLevelAbility(ability).addHint(CreaturesYouControlHint.instance));
    }

    private EvendoWakingHaven(final EvendoWakingHaven card) {
        super(card);
    }

    @Override
    public EvendoWakingHaven copy() {
        return new EvendoWakingHaven(this);
    }
}
