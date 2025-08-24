package mage.cards.u;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UthrosTitanicGodcore extends CardImpl {

    public UthrosTitanicGodcore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLANET);

        // This land enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // Station
        this.addAbility(new StationAbility());

        // STATION 12+
        // {U}, {T}: Add {U} for each artifact you control.
        Ability ability = new DynamicManaAbility(
                Mana.BlueMana(1), ArtifactYouControlCount.instance, new ManaCostsImpl<>("{U}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(new StationLevelAbility(12).withLevelAbility(ability).addHint(ArtifactYouControlHint.instance));
    }

    private UthrosTitanicGodcore(final UthrosTitanicGodcore card) {
        super(card);
    }

    @Override
    public UthrosTitanicGodcore copy() {
        return new UthrosTitanicGodcore(this);
    }
}
