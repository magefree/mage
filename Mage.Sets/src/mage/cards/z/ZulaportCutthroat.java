
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author LevelX2
 */
public final class ZulaportCutthroat extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature you control");

    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public ZulaportCutthroat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.HUMAN, SubType.ROGUE, SubType.ALLY);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Zulaport Cutthroat or another creature you control dies, each opponent loses 1 life and you gain 1 life.
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(new LoseLifeOpponentsEffect(1), false, filter);
        Effect effect = new GainLifeEffect(1);
        effect.setText("and you gain 1 life");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    public ZulaportCutthroat(final ZulaportCutthroat card) {
        super(card);
    }

    @Override
    public ZulaportCutthroat copy() {
        return new ZulaportCutthroat(this);
    }
}
