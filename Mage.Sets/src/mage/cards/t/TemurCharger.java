
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TemurCharger extends CardImpl {

    private static final FilterCard filter = new FilterCard("a green card in your hand");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public TemurCharger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HORSE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Morph - Reveal a green card in your hand.
        this.addAbility(new MorphAbility(new RevealTargetFromHandCost(new TargetCardInHand(filter))));

        // When Temur Charger is turned face up, target creature gains trample until end of turn.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)); 
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private TemurCharger(final TemurCharger card) {
        super(card);
    }

    @Override
    public TemurCharger copy() {
        return new TemurCharger(this);
    }
}
