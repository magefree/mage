package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfAmonkhet extends CardImpl {

    public InvasionOfAmonkhet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{1}{U}{B}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(4);
        this.secondSideCardClazz = mage.cards.l.LazotepConvert.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Amonkhet enters the battlefield, each player mills three cards, then each opponent discards a card and you draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new MillCardsEachPlayerEffect(3, TargetController.EACH_PLAYER)
        );
        ability.addEffect(new DiscardEachPlayerEffect(TargetController.OPPONENT).concatBy(", then"));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and you"));
        this.addAbility(ability);
    }

    private InvasionOfAmonkhet(final InvasionOfAmonkhet card) {
        super(card);
    }

    @Override
    public InvasionOfAmonkhet copy() {
        return new InvasionOfAmonkhet(this);
    }
}
