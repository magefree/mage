package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TurnedFaceUpSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UntapAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EssenceOfAntiquity extends CardImpl {

    public EssenceOfAntiquity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(10);

        // Disguise {2}{W}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{2}{W}")));

        // When Essence of Antiquity is turned face up, creatures you control gain hexproof until end of turn. Untap them.
        Ability ability = new TurnedFaceUpSourceTriggeredAbility(new GainAbilityControlledEffect(
                HexproofAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES
        ));
        ability.addEffect(new UntapAllEffect(StaticFilters.FILTER_CONTROLLED_CREATURE).setText("Untap them"));
        this.addAbility(ability);
    }

    private EssenceOfAntiquity(final EssenceOfAntiquity card) {
        super(card);
    }

    @Override
    public EssenceOfAntiquity copy() {
        return new EssenceOfAntiquity(this);
    }
}
