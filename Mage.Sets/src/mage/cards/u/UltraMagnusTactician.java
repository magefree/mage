package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.MoreThanMeetsTheEyeAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UltraMagnusTactician extends CardImpl {

    public UltraMagnusTactician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{R}{G}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        this.secondSideCardClazz = mage.cards.u.UltraMagnusArmoredCarrier.class;

        // More Than Meets the Eye {2}{R}{G}{W}
        this.addAbility(new MoreThanMeetsTheEyeAbility(this, "{2}{R}{G}{W}"));

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}")));

        // Whenever Ultra Magnus attacks, you may put an artifact creature card from your hand onto the battlefield tapped and attacking. If you do, convert Ultra Magnus at end of combat.
        this.addAbility(new AttacksTriggeredAbility(new UltraMagnusTacticianEffect()));
    }

    private UltraMagnusTactician(final UltraMagnusTactician card) {
        super(card);
    }

    @Override
    public UltraMagnusTactician copy() {
        return new UltraMagnusTactician(this);
    }
}

class UltraMagnusTacticianEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterArtifactCard("artifact creature card");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    UltraMagnusTacticianEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "you may put an artifact creature card from your hand onto the battlefield " +
                "tapped and attacking. If you do, convert {this} at end of combat";
    }

    private UltraMagnusTacticianEffect(final UltraMagnusTacticianEffect effect) {
        super(effect);
    }

    @Override
    public UltraMagnusTacticianEffect copy() {
        return new UltraMagnusTacticianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getHand().isEmpty()) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, 1, filter);
        player.choose(outcome, player.getHand(), target, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, false, null
        );
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return true;
        }
        game.getCombat().addAttackingCreature(permanent.getId(), game);
        game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(
                new TransformSourceEffect().setText("convert {this}")
        ), source);
        return true;
    }
}
