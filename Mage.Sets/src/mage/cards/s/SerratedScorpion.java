package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerratedScorpion extends CardImpl {

    public SerratedScorpion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SCORPION);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // When Serrated Scorpion dies, it deals 2 damage to each opponent and you gain 2 life.
        Ability ability = new DiesSourceTriggeredAbility(new DamagePlayersEffect(
                2, TargetController.OPPONENT, "it"
        ));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private SerratedScorpion(final SerratedScorpion card) {
        super(card);
    }

    @Override
    public SerratedScorpion copy() {
        return new SerratedScorpion(this);
    }
}
