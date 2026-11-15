package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.token.SeleniasCurseToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.replacement.GainDoubleLifeReplacementEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class SeleniaTheCursedHeart extends CardImpl {

    public SeleniaTheCursedHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // If you would gain life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(new GainDoubleLifeReplacementEffect()));

        // When Selenia dies, create a legendary black Aura Curse enchantment token named Selenia's Curse attached to target opponent. The token has enchant player and "If enchanted player would lose life, they lose twice that much life instead."
        Ability ability = new DiesSourceTriggeredAbility(new SeleniaTheCursedHeartEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private SeleniaTheCursedHeart(final SeleniaTheCursedHeart card) {
        super(card);
    }

    @Override
    public SeleniaTheCursedHeart copy() {
        return new SeleniaTheCursedHeart(this);
    }
}

class SeleniaTheCursedHeartEffect extends OneShotEffect {

    SeleniaTheCursedHeartEffect() {
        super(Outcome.Benefit);
        staticText = "create a legendary black Aura Curse enchantment token named Selenia's Curse attached to target opponent. "
            + "The token has enchant player and \"If enchanted player would lose life, they lose twice that much life instead.\"";
    }

    private SeleniaTheCursedHeartEffect(final SeleniaTheCursedHeartEffect effect) {
        super(effect);
    }

    @Override
    public SeleniaTheCursedHeartEffect copy() {
        return new SeleniaTheCursedHeartEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player attachTo = game.getPlayer(getTargetPointer().getFirst(game, source));

        if (controller == null || attachTo == null) {
            return false;
        }

        Token token = new SeleniasCurseToken();
        if (token.putOntoBattlefield(1, game, source)) {
            for (UUID tokenId : token.getLastAddedTokenIds()) {
                attachTo.addAttachment(tokenId, source, game);
            }
        }

        return true;
    }
}
