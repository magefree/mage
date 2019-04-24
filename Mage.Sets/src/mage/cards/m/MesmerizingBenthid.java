package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.MesmerizingBenthidToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MesmerizingBenthid extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.ILLUSION);

    public MesmerizingBenthid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.OCTOPUS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // When Mesmerizing Benthid enters the battlefield, create two 0/2 blue Illusion creature tokens with "Whenever this creature blocks a creature, that creature doesn't untap during its controller's next untap step."
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new MesmerizingBenthidToken(), 2)
        ));

        // Mesmerizing Benthid has hexproof as long as you control an Illusion.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance()),
                new PermanentsOnTheBattlefieldCondition(filter),
                "{this} has hexproof as long as you control an Illusion."
        )));
    }

    private MesmerizingBenthid(final MesmerizingBenthid card) {
        super(card);
    }

    @Override
    public MesmerizingBenthid copy() {
        return new MesmerizingBenthid(this);
    }
}
