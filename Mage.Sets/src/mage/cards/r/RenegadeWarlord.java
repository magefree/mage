package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class RenegadeWarlord extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterAttackingCreature("each other attacking creature");

    public RenegadeWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(1, 0, Duration.EndOfTurn, filter, true), false));
    }

    private RenegadeWarlord(final RenegadeWarlord card) {
        super(card);
    }

    @Override
    public RenegadeWarlord copy() {
        return new RenegadeWarlord(this);
    }
}
