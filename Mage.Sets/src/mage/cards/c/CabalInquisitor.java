package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author cbt33
 */
public final class CabalInquisitor extends CardImpl {

    public CabalInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Threshold - {1}{B}, {T}, Exile two cards from your graveyard: Target player discards a card. Activate this ability only any time you could cast a sorcery, and only if seven or more cards are in your graveyard.
        Ability ability = new ConditionalActivatedAbility(
                new DiscardTargetEffect(1), new ManaCostsImpl<>("{1}{B}"),
                new CardsInControllerGraveyardCondition(7)
        ).setTiming(TimingRule.SORCERY);
        ability.addTarget(new TargetPlayer());
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD)));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private CabalInquisitor(final CabalInquisitor card) {
        super(card);
    }

    @Override
    public CabalInquisitor copy() {
        return new CabalInquisitor(this);
    }
}
