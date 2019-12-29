package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GarrisonGriffin extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.KNIGHT);

    public GarrisonGriffin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Garrison Griffin attacks, target Knight you control gains flying until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private GarrisonGriffin(final GarrisonGriffin card) {
        super(card);
    }

    @Override
    public GarrisonGriffin copy() {
        return new GarrisonGriffin(this);
    }
}
