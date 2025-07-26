package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SymbioteSpawn extends CardImpl {

    public SymbioteSpawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SYMBIOTE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When this creature dies, each opponent loses 2 life and you gain 2 life.
        Ability ability = new DiesSourceTriggeredAbility(new LoseLifeOpponentsEffect(2));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private SymbioteSpawn(final SymbioteSpawn card) {
        super(card);
    }

    @Override
    public SymbioteSpawn copy() {
        return new SymbioteSpawn(this);
    }
}
