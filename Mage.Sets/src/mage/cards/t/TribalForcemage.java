
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllOfChosenSubtypeEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllOfChosenSubtypeEffect;
import mage.abilities.keyword.MorphAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class TribalForcemage extends CardImpl {

    public TribalForcemage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Morph {1}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{1}{G}")));

        // When Tribal Forcemage is turned face up, creatures of the creature type of your choice get +2/+2 and gain trample until end of turn.
        Effect effect = new ChooseCreatureTypeEffect(Outcome.BoostCreature);
        effect.setText("creatures of the creature type of your choice get");
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(effect);
        effect = new BoostAllOfChosenSubtypeEffect(2, 2, Duration.EndOfTurn, false);
        effect.setText(" +2/+2");
        ability.addEffect(effect);
        effect = new GainAbilityAllOfChosenSubtypeEffect(TrampleAbility.getInstance(), Duration.EndOfTurn, new FilterCreaturePermanent());
        effect.setText(" and gain trample until end of turn");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private TribalForcemage(final TribalForcemage card) {
        super(card);
    }

    @Override
    public TribalForcemage copy() {
        return new TribalForcemage(this);
    }
}
