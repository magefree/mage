package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EvieFrye extends CardImpl {

    public EvieFrye(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Partner with Jacob Frye
        this.addAbility(new PartnerWithAbility("Jacob Frye"));

        // {1}, {T}: Draw a card, then discard a card. When you discard a creature card this way, target creature you control can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(new EvieFryeEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private EvieFrye(final EvieFrye card) {
        super(card);
    }

    @Override
    public EvieFrye copy() {
        return new EvieFrye(this);
    }
}

class EvieFryeEffect extends OneShotEffect {

    EvieFryeEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card, then discard a card. When you discard a creature card this way, " +
                "target creature you control can't be blocked this turn";
    }

    private EvieFryeEffect(final EvieFryeEffect effect) {
        super(effect);
    }

    @Override
    public EvieFryeEffect copy() {
        return new EvieFryeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(1, source, game);
        Card card = player.discardOne(false, false, source, game);
        if (card != null && card.isCreature(game)) {
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new CantBeBlockedTargetEffect(), false);
            ability.addTarget(new TargetControlledCreaturePermanent());
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        return true;
    }
}
