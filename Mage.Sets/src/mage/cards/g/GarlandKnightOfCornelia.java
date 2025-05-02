package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GarlandKnightOfCornelia extends CardImpl {

    public GarlandKnightOfCornelia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.secondSideCardClazz = mage.cards.c.ChaosTheEndless.class;

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever you cast a noncreature spell, surveil 1.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new SurveilEffect(1), StaticFilters.FILTER_SPELL_A_NON_CREATURE, false
        ));

        // {3}{B}{B}{R}{R}: Return this card from your graveyard to the battlefield transformed. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new GarlandKnightOfCorneliaEffect(), new ManaCostsImpl<>("{3}{B}{B}{R}{R}")
        ));
    }

    private GarlandKnightOfCornelia(final GarlandKnightOfCornelia card) {
        super(card);
    }

    @Override
    public GarlandKnightOfCornelia copy() {
        return new GarlandKnightOfCornelia(this);
    }
}

class GarlandKnightOfCorneliaEffect extends OneShotEffect {

    GarlandKnightOfCorneliaEffect() {
        super(Outcome.Benefit);
        staticText = "return this card from your graveyard to the battlefield transformed";
    }

    private GarlandKnightOfCorneliaEffect(final GarlandKnightOfCorneliaEffect effect) {
        super(effect);
    }

    @Override
    public GarlandKnightOfCorneliaEffect copy() {
        return new GarlandKnightOfCorneliaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = Optional
                .ofNullable(source.getSourceObjectIfItStillExists(game))
                .filter(Card.class::isInstance)
                .map(Card.class::cast)
                .orElse(null);
        if (player == null || card == null) {
            return false;
        }
        game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + card.getId(), Boolean.TRUE);
        return player.moveCards(card, Zone.BATTLEFIELD, source, game);
    }
}
