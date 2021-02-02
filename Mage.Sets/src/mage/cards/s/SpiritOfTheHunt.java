
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author fireshoes
 */
public final class SpiritOfTheHunt extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each other creature you control that's a Wolf or Werewolf");

    static {
        filter.add(Predicates.or(SubType.WOLF.getPredicate(),
                SubType.WEREWOLF.getPredicate()));
    }

    public SpiritOfTheHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Spirit of the Hunt enters the battlefield, each other creature you control that's a Wolf or a Werewolf gets +0/+3 until end of turn.
        Effect effect = new BoostControlledEffect(0, 3, Duration.EndOfTurn, filter, true);
        effect.setText("each other creature you control that's a Wolf or a Werewolf gets +0/+3 until end of turn");
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect, false));
    }

    private SpiritOfTheHunt(final SpiritOfTheHunt card) {
        super(card);
    }

    @Override
    public SpiritOfTheHunt copy() {
        return new SpiritOfTheHunt(this);
    }
}
