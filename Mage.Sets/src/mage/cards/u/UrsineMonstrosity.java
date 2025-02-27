package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.combat.AttackIfAbleTargetRandomOpponentSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author inuenc
 */
public final class UrsineMonstrosity extends CardImpl {

    public UrsineMonstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.subtype.add(SubType.BEAR);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // At the beginning of combat on your turn, mill a card and choose an opponent at random. Ursine Monstrosity attacks that player this combat if able. Until end of turn, Ursine Monstrosity gains indestructible and gets +1/+1 for each card type among cards in your graveyard.
        BeginningOfCombatTriggeredAbility ability = new BeginningOfCombatTriggeredAbility(
                new MillCardsControllerEffect(1)
        );
        ability.addEffect(new AttackIfAbleTargetRandomOpponentSourceEffect()
                .concatBy("and")
        );
        ability.addEffect(new GainAbilitySourceEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn)
                .setText("Until end of turn, {this} gains indestructible")
        );
        ability.addEffect(new BoostSourceEffect(CardTypesInGraveyardCount.YOU, CardTypesInGraveyardCount.YOU, Duration.EndOfTurn)
                .setText("gets +1/+1 for each card type among cards in your graveyard.")
                .concatBy("and")
        );
        this.addAbility(ability.addHint(CardTypesInGraveyardCount.YOU.getHint()));
    }

    private UrsineMonstrosity(final UrsineMonstrosity card) {
        super(card);
    }

    @Override
    public UrsineMonstrosity copy() {
        return new UrsineMonstrosity(this);
    }
}
