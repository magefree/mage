package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VoiceOfMany extends CardImpl {

    public VoiceOfMany(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Voice of Many enters the battlefield, draw a card for each opponent who controls fewer creatures than you do.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new VoiceOfManyEffect()));
    }

    private VoiceOfMany(final VoiceOfMany card) {
        super(card);
    }

    @Override
    public VoiceOfMany copy() {
        return new VoiceOfMany(this);
    }
}

class VoiceOfManyEffect extends OneShotEffect {

    VoiceOfManyEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card for each opponent who controls fewer creatures than you";
    }

    private VoiceOfManyEffect(final VoiceOfManyEffect effect) {
        super(effect);
    }

    @Override
    public VoiceOfManyEffect copy() {
        return new VoiceOfManyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int myCount = game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game
        ).size();
        int toDraw = game
                .getOpponents(source.getControllerId())
                .stream()
                .mapToInt(uuid -> game.getBattlefield().getAllActivePermanents(
                        StaticFilters.FILTER_PERMANENT_CREATURE, uuid, game
                ).size() < myCount ? 1 : 0)
                .sum();
        if (toDraw == 0) {
            return true;
        }
        return new DrawCardSourceControllerEffect(toDraw).apply(game, source);
    }
}