
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public final class SpurnmageAdvocate extends CardImpl {

    private static final FilterCard filter = new FilterCard("target cards from an opponent's graveyard");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    public SpurnmageAdvocate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: Return two target cards from an opponent's graveyard to their hand. Destroy target attacking creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetCardInASingleGraveyard(2, 2, filter));
        Effect effect = new DestroyTargetEffect();
        effect.setTargetPointer(new SecondTargetPointer());
        ability.addEffect(effect);
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private SpurnmageAdvocate(final SpurnmageAdvocate card) {
        super(card);
    }

    @Override
    public SpurnmageAdvocate copy() {
        return new SpurnmageAdvocate(this);
    }
}
