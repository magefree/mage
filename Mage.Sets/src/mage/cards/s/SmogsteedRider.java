package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FearAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;

/**
 *
 * @author Loki
 */
public final class SmogsteedRider extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("each other attacking creature");

    public SmogsteedRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new AttacksTriggeredAbility(new GainAbilityAllEffect(FearAbility.getInstance(), Duration.EndOfTurn, filter, true), false));
    }

    private SmogsteedRider(final SmogsteedRider card) {
        super(card);
    }

    @Override
    public SmogsteedRider copy() {
        return new SmogsteedRider(this);
    }
}
