package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AngeloToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RinoaHeartilly extends CardImpl {

    public RinoaHeartilly(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Rinoa Heartilly enters, create Angelo, a legendary 1/1 green and white Dog creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new AngeloToken())));

        // Angelo Cannon -- Whenever Rinoa Heartilly attacks, another target creature you control gets +1/+1 until end of turn for each creature you control.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(
                CreaturesYouControlCount.PLURAL, CreaturesYouControlCount.PLURAL
        ).setText("another target creature you control gets +1/+1 until end of turn for each creature you control"));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ANOTHER_TARGET_CREATURE_YOU_CONTROL));
        this.addAbility(ability.withFlavorWord("Angelo Cannon").addHint(CreaturesYouControlHint.instance));
    }

    private RinoaHeartilly(final RinoaHeartilly card) {
        super(card);
    }

    @Override
    public RinoaHeartilly copy() {
        return new RinoaHeartilly(this);
    }
}
