package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ObscuraInitiate extends CardImpl {

    public ObscuraInitiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}{W/B}: Obscura Initiate gains lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilitySourceEffect(LifelinkAbility.getInstance()), new ManaCostsImpl<>("{1}{W/B}")
        ));
    }

    private ObscuraInitiate(final ObscuraInitiate card) {
        super(card);
    }

    @Override
    public ObscuraInitiate copy() {
        return new ObscuraInitiate(this);
    }
}
