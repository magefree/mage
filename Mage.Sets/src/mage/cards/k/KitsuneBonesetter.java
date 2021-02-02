
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.MoreCardsInHandThanOpponentsCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class KitsuneBonesetter extends CardImpl {

    public KitsuneBonesetter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        
        // {tap}: Prevent the next 3 damage that would be dealt to target creature this turn. Activate this ability only if you have more cards in hand than each opponent.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 3),
                new TapSourceCost(),
                MoreCardsInHandThanOpponentsCondition.instance
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KitsuneBonesetter(final KitsuneBonesetter card) {
        super(card);
    }

    @Override
    public KitsuneBonesetter copy() {
        return new KitsuneBonesetter(this);
    }
}
