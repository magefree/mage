package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetDiscard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BorborygmosAndFblthp extends CardImpl {

    public BorborygmosAndFblthp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CYCLOPS);
        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Whenever Borborygmos and Fblthp enters the battlefield or attacks, draw a card, then you may discard any number of land cards. When you discard one or more cards this way, Borborygmos and Fblthp deals twice that much damage to target creature.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new BorborygmosAndFblthpDiscardEffect()));

        // {1}{U}: Put Borborygmos and Fblthp into its owner's library third from the top.
        this.addAbility(new SimpleActivatedAbility(new BorborygmosAndFblthpTuckEffect(), new ManaCostsImpl<>("{1}{U}")));
    }

    private BorborygmosAndFblthp(final BorborygmosAndFblthp card) {
        super(card);
    }

    @Override
    public BorborygmosAndFblthp copy() {
        return new BorborygmosAndFblthp(this);
    }
}

class BorborygmosAndFblthpDiscardEffect extends OneShotEffect {

    BorborygmosAndFblthpDiscardEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card, then you may discard any number of land cards. " +
                "When you discard one or more cards this way, {this} deals twice that much damage to target creature";
    }

    private BorborygmosAndFblthpDiscardEffect(final BorborygmosAndFblthpDiscardEffect effect) {
        super(effect);
    }

    @Override
    public BorborygmosAndFblthpDiscardEffect copy() {
        return new BorborygmosAndFblthpDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(1, source, game);
        TargetDiscard target = new TargetDiscard(
                0, Integer.MAX_VALUE,
                StaticFilters.FILTER_CARD_LANDS, player.getId()
        );
        player.choose(Outcome.Discard, target, source, game);
        Cards cards = player.discard(new CardsImpl(target.getTargets()), false, source, game);
        if (cards.isEmpty()) {
            return true;
        }
        int damage = 2 * cards.size();
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new DamageTargetEffect(damage), false,
                "{this} deals twice that much damage to target creature"
        );
        ability.addTarget(new TargetCreaturePermanent());
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}

class BorborygmosAndFblthpTuckEffect extends OneShotEffect {

    BorborygmosAndFblthpTuckEffect() {
        super(Outcome.Benefit);
        staticText = "put {this} into its owner's library third from the top";
    }

    private BorborygmosAndFblthpTuckEffect(final BorborygmosAndFblthpTuckEffect effect) {
        super(effect);
    }

    @Override
    public BorborygmosAndFblthpTuckEffect copy() {
        return new BorborygmosAndFblthpTuckEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return player != null && permanent != null
                && player.putCardOnTopXOfLibrary(permanent, game, source, 3, true);
    }
}
