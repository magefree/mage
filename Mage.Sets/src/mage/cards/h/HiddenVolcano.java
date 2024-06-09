package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HiddenVolcano extends CardImpl {

    public HiddenVolcano(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.CAVE);

        // Hidden Volcano enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {4}{R}, {T}, Sacrifice Hidden Volcano: Discover 4. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new DiscoverEffect(4), new ManaCostsImpl<>("{4}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private HiddenVolcano(final HiddenVolcano card) {
        super(card);
    }

    @Override
    public HiddenVolcano copy() {
        return new HiddenVolcano(this);
    }
}
