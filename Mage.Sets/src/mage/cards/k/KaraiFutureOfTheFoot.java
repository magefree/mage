package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.SneakCondition;
import mage.abilities.condition.common.SourceEnteredThisTurnCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author muz
 */
public final class KaraiFutureOfTheFoot extends CardImpl {

    public KaraiFutureOfTheFoot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Sneak {2}{W}{B}
        this.addAbility(new SneakAbility(this, "{2}{W}{B}"));

        // Whenever Karai deals combat damage to a player, return target creature card from your graveyard to your hand.
        // If her sneak cost was paid this turn, instead return that card to the battlefield.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
                new ConditionalOneShotEffect(
                        new ReturnFromGraveyardToBattlefieldTargetEffect(),
                        new ReturnFromGraveyardToHandTargetEffect(),
                        new CompoundCondition(SneakCondition.instance, SourceEnteredThisTurnCondition.DID),
                        "return target creature card from your graveyard to your hand. "
                                + "If her sneak cost was paid this turn, instead return that card to the battlefield"
                ), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        this.addAbility(ability);
    }

    private KaraiFutureOfTheFoot(final KaraiFutureOfTheFoot card) {
        super(card);
    }

    @Override
    public KaraiFutureOfTheFoot copy() {
        return new KaraiFutureOfTheFoot(this);
    }
}
