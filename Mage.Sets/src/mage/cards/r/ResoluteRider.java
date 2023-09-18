package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ResoluteRider extends CardImpl {

    public ResoluteRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W/B}{W/B}{W/B}{W/B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // {W/B}{W/B}: Resolute Rider gains lifelink until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{W/B}{W/B}")));

        // {W/B}{W/B}{W/B}: Resolute Rider gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(new GainAbilitySourceEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new ManaCostsImpl<>("{W/B}{W/B}{W/B}")));
    }

    private ResoluteRider(final ResoluteRider card) {
        super(card);
    }

    @Override
    public ResoluteRider copy() {
        return new ResoluteRider(this);
    }
}
