
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class KnightOfTheMists extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Knight");

    static {
        filter.add(new SubtypePredicate(SubType.KNIGHT));
    }

    public KnightOfTheMists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());

        // When Knight of the Mists enters the battlefield, you may pay {U}. If you don't, destroy target Knight and it can't be regenerated.
        Ability ability = new EntersBattlefieldTriggeredAbility(new KnightOfTheMistsEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
        addAbility(ability);
    }

    public KnightOfTheMists(final KnightOfTheMists card) {
        super(card);
    }

    @Override
    public KnightOfTheMists copy() {
        return new KnightOfTheMists(this);
    }
}

class KnightOfTheMistsEffect extends OneShotEffect {

    KnightOfTheMistsEffect() {
        super(Outcome.Neutral);
        this.staticText = "When {this} enters the battlefield, you may pay {U}. If you don't, destroy target Knight and it can't be regenerated.";
    }

    KnightOfTheMistsEffect(final KnightOfTheMistsEffect effect) {
        super(effect);
    }

    @Override
    public KnightOfTheMistsEffect copy() {
        return new KnightOfTheMistsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getSourceId());
        if (player == null) {
            return false;
        }
        Cost cost = new ManaCostsImpl("{U}");
        if (!(cost.canPay(source, source.getSourceId(), player.getId(), game)
                && player.chooseUse(outcome, "Pay {U}?", source, game)
                && cost.pay(source, game, source.getSourceId(), player.getId(), false))) {
            return new DestroyTargetEffect(true).apply(game, source);
        }
        return true;
    }
}
