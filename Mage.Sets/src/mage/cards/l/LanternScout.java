package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LanternScout extends CardImpl {

    public LanternScout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SCOUT);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Rally</i> &mdash; Whenever Lantern Scout or another Ally enters the battlefield under your control, creatures you control gain lifelink until end of turn.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(new GainAbilityAllEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ), false).setAbilityWord(AbilityWord.RALLY));
    }

    private LanternScout(final LanternScout card) {
        super(card);
    }

    @Override
    public LanternScout copy() {
        return new LanternScout(this);
    }
}
