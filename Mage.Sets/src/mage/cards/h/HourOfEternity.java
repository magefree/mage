package mage.cards.h;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.EmptyToken;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author emerald000
 */
public final class HourOfEternity extends CardImpl {

    public HourOfEternity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{U}{U}{U}");

        // Exile X target creature cards from your graveyard. For each card exiled this way, create a token that's a copy of that card, except it's a 4/4 black Zombie.
        this.getSpellAbility().addEffect(new HourOfEternityEffect());
        this.getSpellAbility().setTargetAdjuster(HourOfEternityAdjuster.instance);
    }

    private HourOfEternity(final HourOfEternity card) {
        super(card);
    }

    @Override
    public HourOfEternity copy() {
        return new HourOfEternity(this);
    }
}

enum HourOfEternityAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        Target target = new TargetCardInYourGraveyard(xValue, new FilterCreatureCard((xValue != 1 ? " creature cards" : "creature card") + " from your graveyard"));
        ability.addTarget(target);
    }
}

class HourOfEternityEffect extends OneShotEffect {

    HourOfEternityEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Exile X target creature cards from your graveyard. " +
                "For each card exiled this way, create a token that's a copy of that card, " +
                "except it's a 4/4 black Zombie";
    }

    HourOfEternityEffect(final HourOfEternityEffect effect) {
        super(effect);
    }

    @Override
    public HourOfEternityEffect copy() {
        return new HourOfEternityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> cardsToExile = new HashSet<>(this.getTargetPointer().getTargets(game, source).size());
            for (UUID targetId : this.getTargetPointer().getTargets(game, source)) {
                Card card = controller.getGraveyard().get(targetId, game);
                if (card != null) {
                    cardsToExile.add(card);
                }
            }
            controller.moveCardsToExile(cardsToExile, source, game, true, null, "");
            for (Card card : cardsToExile) {
                if (game.getState().getZone(card.getId()) == Zone.EXILED) {
                    // create token and modify all attributes permanently (without game usage)
                    EmptyToken token = new EmptyToken();
                    CardUtil.copyTo(token).from(card, game);
                    token.removePTCDA();
                    token.setPower(4);
                    token.setToughness(4);
                    token.getColor().setColor(ObjectColor.BLACK);
                    token.removeAllCreatureTypes();
                    token.addSubType(SubType.ZOMBIE);
                    token.putOntoBattlefield(1, game, source, source.getControllerId());
                }
            }
            return true;
        }
        return false;
    }
}
