package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import java.util.UUID;

/**
 * @author ChesseTheWasp
 */
public final class MeditativeSnail extends CardImpl {

    public MeditativeSnail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        
        this.subtype.add(SubType.SLUG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        //draw a card
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));
    }

    private MeditativeSnail(final MeditativeSnail card) {
        super(card);
    }

    @Override
    public MeditativeSnail copy() {
        return new MeditativeSnail(this);
    }
}