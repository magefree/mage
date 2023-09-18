package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SeethingPathblazer extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.ELEMENTAL, "Elemental");

    public SeethingPathblazer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        Ability ability = new SimpleActivatedAbility(new BoostSourceEffect(
                2, 0, Duration.EndOfTurn
        ).setText("{this} gets +2/+0"), new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        ability.addEffect(new GainAbilitySourceEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains first strike until end of turn"));
        this.addAbility(ability);
    }

    private SeethingPathblazer(final SeethingPathblazer card) {
        super(card);
    }

    @Override
    public SeethingPathblazer copy() {
        return new SeethingPathblazer(this);
    }
}
