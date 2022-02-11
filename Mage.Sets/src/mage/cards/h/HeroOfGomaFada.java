
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class HeroOfGomaFada extends CardImpl {

    public HeroOfGomaFada(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // <i>Rally</i> &mdash; Whenever Hero of Goma Fada or another Ally enters the battlefield under your control, creatures you control gain indestructible until end of turn.
        Ability ability = new AllyEntersBattlefieldTriggeredAbility(
                new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURES), false);
        this.addAbility(ability);
    }

    private HeroOfGomaFada(final HeroOfGomaFada card) {
        super(card);
    }

    @Override
    public HeroOfGomaFada copy() {
        return new HeroOfGomaFada(this);
    }
}
