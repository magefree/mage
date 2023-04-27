package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author maurer.it_at_gmail.com, TheElk801
 */
public final class KalastriaHighborn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Vampire you control");

    static {
        filter.add(SubType.VAMPIRE.getPredicate());
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public KalastriaHighborn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Kalastria Highborn or another Vampire you control dies, you may pay {B}. If you do, target player loses 2 life and you gain 2 life.
        Ability ability = new DiesThisOrAnotherCreatureTriggeredAbility(new DoIfCostPaid(new LoseGainEffect(), new ManaCostsImpl<>("{B}")), false, filter);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private KalastriaHighborn(final KalastriaHighborn card) {
        super(card);
    }

    @Override
    public KalastriaHighborn copy() {
        return new KalastriaHighborn(this);
    }
}

class LoseGainEffect extends OneShotEffect {

    LoseGainEffect() {
        super(Outcome.Benefit);
        this.staticText = "target player loses 2 life and you gain 2 life";
    }

    LoseGainEffect(final LoseGainEffect effect) {
        super(effect);
    }

    @Override
    public LoseGainEffect copy() {
        return new LoseGainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Player them = game.getPlayer(source.getFirstTarget());
        if (you == null && them == null) {
            return false;
        }
        if (you != null) {
            you.gainLife(2, game, source);
        }
        if (them != null) {
            them.loseLife(2, game, source, false);
        }
        return true;
    }
}
