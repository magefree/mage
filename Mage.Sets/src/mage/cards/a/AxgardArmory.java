package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardAndOrCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AxgardArmory extends CardImpl {

    public AxgardArmory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Axgard Armory enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {1}{R}{R}{W}, {T}: Sacrifice Axgard Armory: Search your library for an Aura card and/or Equipment card, reveal them, put them into your hand, then shuffle your library.
        Ability ability = new SimpleActivatedAbility(
                new SearchLibraryPutInHandEffect(new TargetCardAndOrCardInLibrary(SubType.AURA, SubType.EQUIPMENT), true),
                new ManaCostsImpl<>("{1}{R}{R}{W}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private AxgardArmory(final AxgardArmory card) {
        super(card);
    }

    @Override
    public AxgardArmory copy() {
        return new AxgardArmory(this);
    }
}
