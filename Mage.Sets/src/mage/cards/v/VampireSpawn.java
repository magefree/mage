package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
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
public final class VampireSpawn extends CardImpl {

    public VampireSpawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When Vampire Spawn enters the battlefield, each opponent loses 2 life and you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeOpponentsEffect(2));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private VampireSpawn(final VampireSpawn card) {
        super(card);
    }

    @Override
    public VampireSpawn copy() {
        return new VampireSpawn(this);
    }
}
