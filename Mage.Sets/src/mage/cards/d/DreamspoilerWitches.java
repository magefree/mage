
package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class DreamspoilerWitches extends CardImpl {

    public DreamspoilerWitches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever you cast a spell during an opponent's turn, you may have target creature get -1/-1 until end of turn.
        Ability ability = new ConditionalTriggeredAbility(new SpellCastControllerTriggeredAbility(new BoostTargetEffect(-1, -1, Duration.EndOfTurn), true), OnOpponentsTurnCondition.instance,
                "Whenever you cast a spell during an opponent's turn, you may have target creature get -1/-1 until end of turn.");
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DreamspoilerWitches(final DreamspoilerWitches card) {
        super(card);
    }

    @Override
    public DreamspoilerWitches copy() {
        return new DreamspoilerWitches(this);
    }
}
