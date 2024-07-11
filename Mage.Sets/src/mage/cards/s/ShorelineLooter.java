package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.discard.DiscardControllerEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShorelineLooter extends CardImpl {

    private static final Condition condition = new InvertCondition(ThresholdCondition.instance);

    public ShorelineLooter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.RAT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Shoreline Looter can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Threshold -- Whenever Shoreline Looter deals combat damage to a player, draw a card. Then discard a card unless seven or more cards are in your graveyard.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(1));
        ability.addEffect(new ConditionalOneShotEffect(
                new DiscardControllerEffect(1), condition,
                "then discard a card unless seven or more cards are in your graveyard"
        ));
        this.addAbility(ability.setAbilityWord(AbilityWord.THRESHOLD));
    }

    private ShorelineLooter(final ShorelineLooter card) {
        super(card);
    }

    @Override
    public ShorelineLooter copy() {
        return new ShorelineLooter(this);
    }
}
