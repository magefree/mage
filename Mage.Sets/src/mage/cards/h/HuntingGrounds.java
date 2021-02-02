
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author LoneFox
 */
public final class HuntingGrounds extends CardImpl {

    public HuntingGrounds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}{W}");

        // Threshold - As long as seven or more cards are in your graveyard, Hunting Grounds has "Whenever an opponent casts a spell, you may put a creature card from your hand onto the battlefield."
        Ability gainedAbility = new SpellCastOpponentTriggeredAbility(new PutCardFromHandOntoBattlefieldEffect(
            new FilterCreatureCard("a creature card")), true);
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(gainedAbility, Duration.WhileOnBattlefield), new CardsInControllerGraveyardCondition(7),
            "As long as seven or more cards are in your graveyard, {this} has \"Whenever an opponent casts a spell, you may put a creature card from your hand onto the battlefield.\""));
        ability.setAbilityWord(AbilityWord.THRESHOLD);
        this.addAbility(ability);
    }

    private HuntingGrounds(final HuntingGrounds card) {
        super(card);
    }

    @Override
    public HuntingGrounds copy() {
        return new HuntingGrounds(this);
    }
}
