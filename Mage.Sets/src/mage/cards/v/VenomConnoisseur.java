package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllianceAbility;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenomConnoisseur extends CardImpl {

    public VenomConnoisseur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Alliance — Whenever another creature enters the battlefield under your control, Venom Connoisseur gains deathtouch until end of turn. If this is the second time this ability has resolved this turn, all creatures you control gain deathtouch until end of turn.
        Ability ability = new AllianceAbility(new GainAbilitySourceEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ));
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(
                Outcome.AddAbility, 2,
                new GainAbilityAllEffect(
                        DeathtouchAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("all creatures you control gain deathtouch until end of turn")
        ));
        this.addAbility(ability);
    }

    private VenomConnoisseur(final VenomConnoisseur card) {
        super(card);
    }

    @Override
    public VenomConnoisseur copy() {
        return new VenomConnoisseur(this);
    }
}
