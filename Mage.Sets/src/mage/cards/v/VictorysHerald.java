package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author Loki
 */
public final class VictorysHerald extends CardImpl {

    public VictorysHerald (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Victory's Herald attacks, attacking creatures gain flying and lifelink until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityAllEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, "attacking creatures gain flying"), false);
        ability.addEffect(new GainAbilityAllEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, "and lifelink until end of turn"));
        this.addAbility(ability);
    }

    public VictorysHerald (final VictorysHerald card) {
        super(card);
    }

    @Override
    public VictorysHerald copy() {
        return new VictorysHerald(this);
    }

}
