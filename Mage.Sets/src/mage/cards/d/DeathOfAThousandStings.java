
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.MoreCardsInHandThanOpponentsCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DeathOfAThousandStings extends CardImpl {

    public DeathOfAThousandStings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{B}");
        this.subtype.add(SubType.ARCANE);

        // Target player loses 1 life and you gain 1 life.
        this.getSpellAbility().addEffect(new LoseLifeTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetPlayer());
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        this.getSpellAbility().addEffect(effect);

        // At the beginning of your upkeep, if you have more cards in hand than each opponent, you may return Death of a Thousand Stings from your graveyard to your hand.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.GRAVEYARD,
                        new ReturnSourceFromGraveyardToHandEffect(),
                        TargetController.YOU, true),
                MoreCardsInHandThanOpponentsCondition.instance,
                "At the beginning of your upkeep, if you have more cards in hand than each opponent, you may return {this} from your graveyard to your hand.");
        this.addAbility(ability);
    }

    private DeathOfAThousandStings(final DeathOfAThousandStings card) {
        super(card);
    }

    @Override
    public DeathOfAThousandStings copy() {
        return new DeathOfAThousandStings(this);
    }
}
