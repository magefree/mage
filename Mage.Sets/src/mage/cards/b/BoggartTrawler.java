package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.ExileGraveyardAllTargetPlayerEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author grimreap124
 */
public final class BoggartTrawler extends ModalDoubleFacedCard {

    public BoggartTrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.GOBLIN}, "{2}{B}",
                "Boggart Bog", new CardType[]{CardType.LAND}, new SubType[]{}, "");

        this.getLeftHalfCard().setPT(new MageInt(3), new MageInt(1));

        // When Boggart Trawler enters the battlefield, exile target player's graveyard.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(
                new ExileGraveyardAllTargetPlayerEffect());
        ability.addTarget(new TargetPlayer());
        this.getLeftHalfCard().addAbility(ability);

        // As Boggart Bog enters the battlefield, you may pay 3 life. If you don’t, it enters the battlefield tapped.
        this.getRightHalfCard()
                .addAbility(new AsEntersBattlefieldAbility(new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                        "you may pay 3 life. If you don't, it enters the battlefield tapped"));
        this.getRightHalfCard().addAbility(new BlackManaAbility());

    }

    private BoggartTrawler(final BoggartTrawler card) {
        super(card);
    }

    @Override
    public BoggartTrawler copy() {
        return new BoggartTrawler(this);
    }
}
