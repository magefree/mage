package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MirkwoodSpider extends CardImpl {

    public MirkwoodSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Mirkwood Spider attacks, target legendary creature you control gains deathtouch until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn
        ));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_CREATURE_LEGENDARY));
        this.addAbility(ability);
    }

    private MirkwoodSpider(final MirkwoodSpider card) {
        super(card);
    }

    @Override
    public MirkwoodSpider copy() {
        return new MirkwoodSpider(this);
    }
}
