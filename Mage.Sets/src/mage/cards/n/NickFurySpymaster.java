package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author notshauna
 */

public final class NickFurySpymaster extends CardImpl {

    public NickFurySpymaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SPY);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        //First Strike
        this.addAbility(FirstStrikeAbility.getInstance());

        //Whenever a creature you control attacks alone, draw a card. Then you may put a creature card with mana value 3 or less from your hand onto the battlefield.
        // It enters tapped and attacking and gains indestructible until end of turn.
        Ability ability = new AttacksAloneControlledTriggeredAbility(
                new DrawCardSourceControllerEffect(1));
        ability.addEffect(new NickFurySpymasterEffect());
        this.addAbility(ability);
    }

    private NickFurySpymaster(final NickFurySpymaster card){
        super(card);
    }

    @Override
    public NickFurySpymaster copy() {
        return new NickFurySpymaster(this);
    }
}

class NickFurySpymasterEffect extends OneShotEffect {

    private static final FilterCreatureCard filter = new FilterCreatureCard(" a creature card with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public NickFurySpymasterEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "Then you may put a creature card with mana value 3 or less from your hand onto the battlefield." +
                " It enters tapped and attacking and gains indestructible until end of turn.";
    }

    private NickFurySpymasterEffect(final NickFurySpymasterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        TargetCard target = new TargetCardInHand(0,1, filter);
        if(!player.choose(outcome, player.getHand(), target, source, game)) {
            return false;
        }

        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }

        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, true, null);

        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return false;
        }

        game.getCombat().addAttackingCreature(permanent.getId(), game);

        // Gains indestructable until end of turn
        ContinuousEffect effect = new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn);
        effect.setTargetPointer(new FixedTarget(permanent.getId(), game));
        game.addEffect(effect, source);

        return true;
    }

    @Override
    public NickFurySpymasterEffect copy() {
        return new NickFurySpymasterEffect(this);
    }
}