package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfErgamon extends CardImpl {

    public InvasionOfErgamon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{R}{G}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(5);
        this.secondSideCardClazz = mage.cards.t.TrugaCliffcharger.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Ergamon enters the battlefield, create a Treasure token. Then you may discard a card. If you do, draw a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken()));
        ability.addEffect(new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new DiscardCardCost()).concatBy("Then"));
        this.addAbility(ability);
    }

    private InvasionOfErgamon(final InvasionOfErgamon card) {
        super(card);
    }

    @Override
    public InvasionOfErgamon copy() {
        return new InvasionOfErgamon(this);
    }
}
