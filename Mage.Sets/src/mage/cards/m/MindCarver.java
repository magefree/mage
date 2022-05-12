package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CardsInOpponentGraveyardCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author TheElk801
 */
public final class MindCarver extends CardImpl {

    public MindCarver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // When Mind Carver enters the battlefield, attach it to target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AttachEffect(
                Outcome.BoostCreature, "attach it to target creature you control"
        ), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // Equipped Creature gets +1/+0. It gets +3/+1 instead as long as an opponent has eight or more cards in their graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostEquippedEffect(3, 1),
                new BoostEquippedEffect(1, 0),
                CardsInOpponentGraveyardCondition.EIGHT, "Equipped creature gets +1/+0. " +
                "It gets +3/+1 instead as long as an opponent has eight or more cards in their graveyard."
        )).addHint(CardsInOpponentGraveyardCondition.EIGHT.getHint()));

        // Equip {2}{B}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{2}{B}"), false));
    }

    private MindCarver(final MindCarver card) {
        super(card);
    }

    @Override
    public MindCarver copy() {
        return new MindCarver(this);
    }
}
