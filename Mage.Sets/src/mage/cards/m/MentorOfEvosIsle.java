package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ChooseACardInYourHandItPerpetuallyGainsEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author anonymous
 */
public final class MentorOfEvosIsle extends CardImpl {

    public MentorOfEvosIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Mentor of Evos Isle enters the battlefield, choose a creature card in your hand. It perpetually gains flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ChooseACardInYourHandItPerpetuallyGainsEffect(FlyingAbility.getInstance(), new FilterCreatureCard())));
    }

    private MentorOfEvosIsle(final MentorOfEvosIsle card) {
        super(card);
    }

    @Override
    public MentorOfEvosIsle copy() {
        return new MentorOfEvosIsle(this);
    }
}
