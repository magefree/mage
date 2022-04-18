package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class HowltoothHollow extends CardImpl {

    private static final Condition condition
            = new CardsInHandCondition(ComparisonType.EQUAL_TO, 0, null, TargetController.ANY);

    public HowltoothHollow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // Hideaway
        this.addAbility(new HideawayAbility(4));
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {tap}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {B}, {tap}: You may play the exiled card without paying its mana cost if each player has no cards in hand.
        Ability ability = new SimpleActivatedAbility(new ConditionalOneShotEffect(
                new HideawayPlayEffect(), condition, "you may play the exiled card " +
                "without paying its mana cost if each player has no cards in hand"
        ), new ManaCostsImpl("{B}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private HowltoothHollow(final HowltoothHollow card) {
        super(card);
    }

    @Override
    public HowltoothHollow copy() {
        return new HowltoothHollow(this);
    }
}
