package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BebopSkullAndCrossbones extends CardImpl {

    public BebopSkullAndCrossbones(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BOAR);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Partner with Rocksteady, Mutant Marauder
        this.addAbility(new PartnerWithAbility("Rocksteady, Mutant Marauder"));

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Bebop deals combat damage to a player, you may draw X cards, where X is the number of counters on Bebop. If you do, you lose X life.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new BebopSkullAndCrossbonesEffect()));
    }

    private BebopSkullAndCrossbones(final BebopSkullAndCrossbones card) {
        super(card);
    }

    @Override
    public BebopSkullAndCrossbones copy() {
        return new BebopSkullAndCrossbones(this);
    }
}

class BebopSkullAndCrossbonesEffect extends OneShotEffect {

    BebopSkullAndCrossbonesEffect() {
        super(Outcome.Benefit);
        staticText = "you may draw X cards, where X is the number of counters on Bebop. If you do, you lose X life";
    }

    private BebopSkullAndCrossbonesEffect(final BebopSkullAndCrossbonesEffect effect) {
        super(effect);
    }

    @Override
    public BebopSkullAndCrossbonesEffect copy() {
        return new BebopSkullAndCrossbonesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int count = CountersSourceCount.ANY.calculate(game, source, this);
        if (player == null
                || count < 1
                || !player.chooseUse(Outcome.DrawCard, "Draw " + CardUtil.numberToText(count, "a") +
                " card" + (count > 1 ? "s" : "") + '?', source, game)
                || player.drawCards(count, source, game) <= 0) {
            return false;
        }
        player.loseLife(count, game, source, false);
        return true;
    }
}
