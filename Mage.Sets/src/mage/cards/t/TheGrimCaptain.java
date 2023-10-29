package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInExile;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheGrimCaptain extends CardImpl {

    public TheGrimCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);
        this.nightCard = true;
        this.color.setBlack(true);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Whenever The Grim Captain attacks, each opponent sacrifices a nonland permanent. Then you may put an exiled creature card used to craft The Grim Captain onto the battlefield under your control tapped and attacking.
        Ability ability = new AttacksTriggeredAbility(new SacrificeOpponentsEffect(StaticFilters.FILTER_PERMANENT_NON_LAND));
        ability.addEffect(new TheGrimCaptainEffect());
        this.addAbility(ability);
    }

    private TheGrimCaptain(final TheGrimCaptain card) {
        super(card);
    }

    @Override
    public TheGrimCaptain copy() {
        return new TheGrimCaptain(this);
    }
}

class TheGrimCaptainEffect extends OneShotEffect {

    TheGrimCaptainEffect() {
        super(Outcome.Benefit);
        staticText = "Then you may put an exiled creature card used to craft {this} " +
                "onto the battlefield under your control tapped and attacking";
    }

    private TheGrimCaptainEffect(final TheGrimCaptainEffect effect) {
        super(effect);
    }

    @Override
    public TheGrimCaptainEffect copy() {
        return new TheGrimCaptainEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInExile(
                0, 1, StaticFilters.FILTER_CARD_CREATURE,
                CardUtil.getExileZoneId(game, source, -2)
        );
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        game.getCombat().addAttackingCreature(card.getId(), game);
        return true;
    }
}
