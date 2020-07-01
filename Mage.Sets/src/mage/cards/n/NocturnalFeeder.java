package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NocturnalFeeder extends CardImpl {

    public NocturnalFeeder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Nocturnal Feeder dies, each opponent loses 2 life and you gain 2 life.
        Ability ability = new DiesSourceTriggeredAbility(new LoseLifeOpponentsEffect(2));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private NocturnalFeeder(final NocturnalFeeder card) {
        super(card);
    }

    @Override
    public NocturnalFeeder copy() {
        return new NocturnalFeeder(this);
    }
}
