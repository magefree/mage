package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ChooseACardInYourHandItPerpetuallyGainsEffect;
import mage.constants.SubType;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author karapuzz14
 */
public final class LeoninSanctifier extends CardImpl {

    public LeoninSanctifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Leonin Sanctifier enters the battlefield, choose a creature card in your hand. It perpetually gains lifelink.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ChooseACardInYourHandItPerpetuallyGainsEffect(LifelinkAbility.getInstance(), new FilterCreatureCard())));
    }

    private LeoninSanctifier(final LeoninSanctifier card) {
        super(card);
    }

    @Override
    public LeoninSanctifier copy() {
        return new LeoninSanctifier(this);
    }
}
