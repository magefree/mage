
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class AphettoExterminator extends CardImpl {

    public AphettoExterminator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN, SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Morph {3}{B}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{3}{B}")));

        // When Aphetto Exterminator is turned face up, target creature gets -3/-3 until end of turn.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new BoostTargetEffect(-3,-3,Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private AphettoExterminator(final AphettoExterminator card) {
        super(card);
    }

    @Override
    public AphettoExterminator copy() {
        return new AphettoExterminator(this);
    }
}
