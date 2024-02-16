package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.PackTacticsAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MinionOfTheMighty extends CardImpl {

    public MinionOfTheMighty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.KOBOLD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Pack tactics — Whenever Minion of the Mighty attacks, if you attacked with creatures wih total power 6 or greater this combat, you may put a Dragon creature card from your hand onto the battlefield tapped and attacking.
        this.addAbility(new PackTacticsAbility(new MinionOfTheMightyEffect()));
    }

    private MinionOfTheMighty(final MinionOfTheMighty card) {
        super(card);
    }

    @Override
    public MinionOfTheMighty copy() {
        return new MinionOfTheMighty(this);
    }
}

class MinionOfTheMightyEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard("a Dragon creature card");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    MinionOfTheMightyEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a Dragon creature card from your hand onto the battlefield tapped and attacking";
    }

    private MinionOfTheMightyEffect(final MinionOfTheMightyEffect effect) {
        super(effect);
    }

    @Override
    public MinionOfTheMightyEffect copy() {
        return new MinionOfTheMightyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        TargetCardInHand target = new TargetCardInHand(filter);
        if (controller == null) {
            return false;
        }
        target.choose(outcome, controller.getId(), source.getSourceId(), source, game);
        Card card = controller.getHand().get(target.getFirstTarget(), game);
        if (card == null) {
            return false;
        }
        controller.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, true, null
        );
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null) {
            game.getCombat().addAttackingCreature(permanent.getId(), game);
        }
        return true;
    }
}
