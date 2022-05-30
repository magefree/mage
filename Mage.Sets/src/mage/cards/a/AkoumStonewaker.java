
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.AkoumStonewakerElementalToken;

/**
 *
 * @author LevelX2
 */
public final class AkoumStonewaker extends CardImpl {

    public AkoumStonewaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, you may pay {2}{R}. If you do, create a 3/1 red Elemental creature token with trample and haste.
        // Exile that token at the beginning of the next end step.
        this.addAbility(new LandfallAbility(new DoIfCostPaid(new AkoumStonewakerEffect(), new ManaCostsImpl<>("{2}{R}")), false));

    }

    private AkoumStonewaker(final AkoumStonewaker card) {
        super(card);
    }

    @Override
    public AkoumStonewaker copy() {
        return new AkoumStonewaker(this);
    }
}

class AkoumStonewakerEffect extends OneShotEffect {

    public AkoumStonewakerEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "create a 3/1 red Elemental creature token with trample and haste. Exile that token at the beginning of the next end step";
    }

    public AkoumStonewakerEffect(final AkoumStonewakerEffect effect) {
        super(effect);
    }

    @Override
    public AkoumStonewakerEffect copy() {
        return new AkoumStonewakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        CreateTokenEffect effect = new CreateTokenEffect(new AkoumStonewakerElementalToken());
        if (effect.apply(game, source)) {
            effect.exileTokensCreatedAtNextEndStep(game, source);
            return true;
        }
        return false;
    }
}
