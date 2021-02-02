
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author LevelX2
 */
public final class IroncladSlayer extends CardImpl {

    private static final FilterCard filter = new FilterCard("Aura or Equipment card from your graveyard");

    static {
        filter.add(Predicates.or(SubType.AURA.getPredicate(), SubType.EQUIPMENT.getPredicate()));
    }

    public IroncladSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Ironclad Slayer enters the battlefield, you may return target Aura or Equipment card from your graveyard to your hand.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("you may return target Aura or Equipment card from your graveyard to your hand");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, true);
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private IroncladSlayer(final IroncladSlayer card) {
        super(card);
    }

    @Override
    public IroncladSlayer copy() {
        return new IroncladSlayer(this);
    }
}
