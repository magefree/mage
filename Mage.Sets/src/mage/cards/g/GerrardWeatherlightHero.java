package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.PutIntoGraveFromBattlefieldThisTurnPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.CardsPutIntoGraveyardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GerrardWeatherlightHero extends CardImpl {

    public GerrardWeatherlightHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Gerrard, Weatherlight Hero dies, exile it and return to the battlefield all artifact and creature cards in your graveyard that were put there from the battlefield this turn.
        Ability ability = new DiesSourceTriggeredAbility(new ExileSourceEffect().setText("exile it"));
        ability.addEffect(new GerrardWeatherlightHeroEffect());
        this.addAbility(ability, new CardsPutIntoGraveyardWatcher());
    }

    private GerrardWeatherlightHero(final GerrardWeatherlightHero card) {
        super(card);
    }

    @Override
    public GerrardWeatherlightHero copy() {
        return new GerrardWeatherlightHero(this);
    }
}

class GerrardWeatherlightHeroEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(PutIntoGraveFromBattlefieldThisTurnPredicate.instance);
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    GerrardWeatherlightHeroEffect() {
        super(Outcome.Benefit);
        staticText = "and return to the battlefield all artifact and creature cards " +
                "in your graveyard that were put there from the battlefield this turn";
    }

    private GerrardWeatherlightHeroEffect(final GerrardWeatherlightHeroEffect effect) {
        super(effect);
    }

    @Override
    public GerrardWeatherlightHeroEffect copy() {
        return new GerrardWeatherlightHeroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return player.moveCards(player.getGraveyard().getCards(
                filter, source.getControllerId(), source, game
        ), Zone.BATTLEFIELD, source, game);
    }
}
// don’t mourn for me. this is my destiny.
