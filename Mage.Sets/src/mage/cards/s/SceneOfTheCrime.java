package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.token.ClueAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SceneOfTheCrime extends CardImpl {

    public SceneOfTheCrime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.LAND}, "");

        this.subtype.add(SubType.CLUE);

        // Scene of the Crime enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {T}, Tap an untapped creature you control: Add one mana of any color.
        Ability ability = new AnyColorManaAbility();
        ability.addCost(new TapTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE)));
        this.addAbility(ability);

        // {2}, Sacrifice Scene of the Crime: Draw a card.
        this.addAbility(new ClueAbility(true));
    }

    private SceneOfTheCrime(final SceneOfTheCrime card) {
        super(card);
    }

    @Override
    public SceneOfTheCrime copy() {
        return new SceneOfTheCrime(this);
    }
}
