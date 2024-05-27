package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DredgeAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.PlantToken;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;

/**
 *
 * @author Grath
 */
public final class TheNecrobloom extends CardImpl {

    public TheNecrobloom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PLANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(7);

        // Landfall -- Whenever a land enters the battlefield under your control, create a 0/1 green Plant creature
        // token. If you control seven or more lands with different names, create a 2/2 black Zombie creature token
        // instead.
        this.addAbility(new LandfallAbility(new ConditionalOneShotEffect(
                new CreateTokenEffect(new ZombieToken()),
                new CreateTokenEffect(new PlantToken()),
                TheNecrobloomCondition.instance, "create a 0/1 green Plant creature token. If you control " +
                "seven or more lands with different names, create 2/2 black Zombie creature token instead"
        )));

        // Land cards in your graveyard have dredge 2.
        this.addAbility(new SimpleStaticAbility(new TheNecrobloomDredgeEffect()));
    }

    private TheNecrobloom(final TheNecrobloom card) {
        super(card);
    }

    @Override
    public TheNecrobloom copy() {
        return new TheNecrobloom(this);
    }
}

enum TheNecrobloomCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        return game
                .getBattlefield()
                .getAllActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), game)
                .stream()
                .map(permanent -> permanent.getName())
                .filter(s -> s.length() > 0)
                .distinct()
                .count() > 6;
    }
}

class TheNecrobloomDredgeEffect extends ContinuousEffectImpl {

    TheNecrobloomDredgeEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "Land cards in your graveyard have dredge 2.";
    }

    private TheNecrobloomDredgeEffect(final TheNecrobloomDredgeEffect effect) {
        super(effect);
    }

    @Override
    public TheNecrobloomDredgeEffect copy() {
        return new TheNecrobloomDredgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Card card : player.getGraveyard().getCards(StaticFilters.FILTER_CARD_LAND, game)) {
            Ability ability = new DredgeAbility(2);
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
        return true;
    }
}