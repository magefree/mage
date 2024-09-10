package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DoorsOfDurin extends CardImpl {

    public DoorsOfDurin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);

        // Whenever you attack, scry 2, then you may reveal the top card of your library. If it's a creature card, put it onto the battlefield tapped and attacking. Until your next turn, it gains trample if you control a Dwarf and hexproof if you control an Elf.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DoorsOfDurinEffect(), 1));
    }

    private DoorsOfDurin(final DoorsOfDurin card) {
        super(card);
    }

    @Override
    public DoorsOfDurin copy() {
        return new DoorsOfDurin(this);
    }
}

class DoorsOfDurinEffect extends OneShotEffect {

    private static final FilterPermanent filter1 = new FilterControlledPermanent(SubType.DWARF);
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.ELF);

    DoorsOfDurinEffect() {
        super(Outcome.Benefit);
        staticText = "scry 2, then you may reveal the top card of your library. If it's a creature card, " +
                "put it onto the battlefield tapped and attacking. Until your next turn, " +
                "it gains trample if you control a Dwarf and hexproof if you control an Elf";
    }

    private DoorsOfDurinEffect(final DoorsOfDurinEffect effect) {
        super(effect);
    }

    @Override
    public DoorsOfDurinEffect copy() {
        return new DoorsOfDurinEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.scry(2, source, game);
        Card card = player.getLibrary().getFromTop(game);
        if (card == null || !player.chooseUse(outcome, "Reveal " + card.getIdName() + '?', source, game)) {
            return false;
        }
        player.revealCards(source, new CardsImpl(card), game);
        if (!card.isCreature(game)) {
            return true;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return true;
        }
        game.getCombat().addAttackingCreature(permanent.getId(), game);
        if (game.getBattlefield().contains(filter1, source, game, 1)) {
            game.addEffect(new GainAbilityTargetEffect(
                    TrampleAbility.getInstance(), Duration.UntilYourNextTurn
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        if (game.getBattlefield().contains(filter2, source, game, 1)) {
            game.addEffect(new GainAbilityTargetEffect(
                    HexproofAbility.getInstance(), Duration.UntilYourNextTurn
            ).setTargetPointer(new FixedTarget(permanent, game)), source);
        }
        return true;
    }
}
