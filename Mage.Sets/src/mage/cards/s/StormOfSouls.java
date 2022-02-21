package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class StormOfSouls extends CardImpl {
    public StormOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Return all creature cards from your graveyard to the battlefield.
        // Each of them is a 1/1 Spirit with flying in addition to its other types.
        this.getSpellAbility().addEffect(new StormOfSoulsReturnEffect());

        // Exile Storm of Souls.
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private StormOfSouls(final StormOfSouls card) {
        super(card);
    }

    @Override
    public StormOfSouls copy() {
        return new StormOfSouls(this);
    }
}

class StormOfSoulsReturnEffect extends OneShotEffect {
    public StormOfSoulsReturnEffect() {
        super(Outcome.Benefit);
        staticText = "Return all creature cards from your graveyard to the battlefield. " +
                "Each of them is a 1/1 Spirit with flying in addition to its other types.";
    }

    private StormOfSoulsReturnEffect(final StormOfSoulsReturnEffect effect) {
        super(effect);
    }

    @Override
    public StormOfSoulsReturnEffect copy() {
        return new StormOfSoulsReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game));
        if (cards.isEmpty()) {
            return false;
        }

        player.moveCards(cards, Zone.BATTLEFIELD, source, game);

        // Figure out which cards were successfuly moved so that they can be turned into 1/1 Spirits
        cards.retainZone(Zone.BATTLEFIELD, game);

        // Change the creatures
        game.addEffect(new StormOfSoulsChangeCreatureEffect().setTargetPointer(new FixedTargets(cards, game)), source);
        return true;
    }
}

class StormOfSoulsChangeCreatureEffect extends ContinuousEffectImpl {

    public StormOfSoulsChangeCreatureEffect() {
        super(Duration.Custom, Outcome.Benefit);
    }

    private StormOfSoulsChangeCreatureEffect(final StormOfSoulsChangeCreatureEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        // Each of them is a 1/1 Spirit with flying in addition to its other types
        for (UUID cardID : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(cardID);
            if (permanent == null) {
                continue;
            }

            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, SubType.SPIRIT);
                    break;
                case AbilityAddingRemovingEffects_6:
                    permanent.addAbility(FlyingAbility.getInstance(), source.getSourceId(), game);
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setValue(1);
                        permanent.getToughness().setValue(1);
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public StormOfSoulsChangeCreatureEffect copy() {
        return new StormOfSoulsChangeCreatureEffect(this);
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4
                || layer == Layer.AbilityAddingRemovingEffects_6
                || layer == Layer.PTChangingEffects_7;
    }
}
