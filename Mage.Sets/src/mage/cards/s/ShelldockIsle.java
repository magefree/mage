package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInAnyLibraryCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ShelldockIsle extends CardImpl {

    private static final Condition condition = new CardsInAnyLibraryCondition(ComparisonType.FEWER_THAN, 21);

    public ShelldockIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Hideaway
        this.addAbility(new HideawayAbility(4));
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {tap}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {U}, {tap}: You may play the exiled card without paying its mana cost if a library has twenty or fewer cards in it.
        Ability ability = new SimpleActivatedAbility(new ConditionalOneShotEffect(
                new HideawayPlayEffect(), condition, "you may play the exiled card " +
                "without paying its mana cost if a library has twenty or fewer cards in it"
        ), new ManaCostsImpl("{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ShelldockIsle(final ShelldockIsle card) {
        super(card);
    }

    @Override
    public ShelldockIsle copy() {
        return new ShelldockIsle(this);
    }
}
