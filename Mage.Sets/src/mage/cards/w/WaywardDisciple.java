
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public final class WaywardDisciple extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public WaywardDisciple(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Whenever Wayward Disciple or another creature you control dies, target opponent loses 1 life and you gain 1 life.
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(new LoseLifeTargetEffect(1), false, filter);
        ability.addTarget(new TargetOpponent());
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public WaywardDisciple(final WaywardDisciple card) {
        super(card);
    }

    @Override
    public WaywardDisciple copy() {
        return new WaywardDisciple(this);
    }
}
