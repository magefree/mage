package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class WinterTeamPlayer extends CardImpl {

    public WinterTeamPlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Whenever you cast a noncreature spell, creatures you control get +1/+0 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new BoostControlledEffect(1, 0, Duration.EndOfTurn),
            StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));
    }

    private WinterTeamPlayer(final WinterTeamPlayer card) {
        super(card);
    }

    @Override
    public WinterTeamPlayer copy() {
        return new WinterTeamPlayer(this);
    }
}
