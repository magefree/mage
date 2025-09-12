package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IcetillExplorer extends CardImpl {

    public IcetillExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)));

        // You may play lands from your graveyard.
        this.addAbility(new SimpleStaticAbility(PlayFromGraveyardControllerEffect.playLands()));

        // Landfall -- Whenever a land you control enters, mill a card.
        this.addAbility(new LandfallAbility(new MillCardsControllerEffect(1)));
    }

    private IcetillExplorer(final IcetillExplorer card) {
        super(card);
    }

    @Override
    public IcetillExplorer copy() {
        return new IcetillExplorer(this);
    }
}
