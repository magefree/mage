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
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DreamSpoilers extends CardImpl {

    public DreamSpoilers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WARLOCK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever you cast a spell during an opponent's turn, up to one target creature an opponent controls gets -1/-1 until end of turn.
        Ability ability = new ConditionalTriggeredAbility(
                new SpellCastControllerTriggeredAbility(
                        new BoostTargetEffect(-1, -1, Duration.EndOfTurn), false
                ), OnOpponentsTurnCondition.instance, "Whenever you cast a spell during an opponent's "
                + "turn, up to one target creature an opponent controls gets -1/-1 until end of turn."
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private DreamSpoilers(final DreamSpoilers card) {
        super(card);
    }

    @Override
    public DreamSpoilers copy() {
        return new DreamSpoilers(this);
    }
}