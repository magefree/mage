package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileUntilSourceLeavesEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author awjackson
 */
public final class BanisherPriest extends CardImpl {

    public BanisherPriest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}");
        this.subtype.add(SubType.HUMAN, SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Banisher Priest enters the battlefield, exile target creature an opponent controls until Banisher Priest leaves the battlefield.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileUntilSourceLeavesEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private BanisherPriest(final BanisherPriest card) {
        super(card);
    }

    @Override
    public BanisherPriest copy() {
        return new BanisherPriest(this);
    }
}
