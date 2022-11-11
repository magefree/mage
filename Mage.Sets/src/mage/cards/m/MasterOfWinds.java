package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MasterOfWinds extends CardImpl {

    public MasterOfWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Master of Winds enters the battlefield, draw two cards, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(2, 1)));

        // Whenever you cast an instant, sorcery, or Wizard spell, you may have Master of Winds's base power and toughness become 4/1 or 1/4 until end of turn.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new MasterOfWindsEffect(), StaticFilters.FILTER_SPELL_INSTANT_SORCERY_WIZARD, true
        ));
    }

    private MasterOfWinds(final MasterOfWinds card) {
        super(card);
    }

    @Override
    public MasterOfWinds copy() {
        return new MasterOfWinds(this);
    }
}

class MasterOfWindsEffect extends OneShotEffect {

    MasterOfWindsEffect() {
        super(Outcome.Benefit);
        staticText = "have {this}'s base power and toughness become 4/1 or 1/4 until end of turn";
    }

    private MasterOfWindsEffect(final MasterOfWindsEffect effect) {
        super(effect);
    }

    @Override
    public MasterOfWindsEffect copy() {
        return new MasterOfWindsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int power = player.chooseUse(
                Outcome.Neutral, "Have this creature become a 4/1 or a 1/4?",
                null, "4/1", "1/4", source, game
        ) ? 4 : 1;
        game.addEffect(new SetBasePowerToughnessSourceEffect(
                power, 5 - power, Duration.EndOfTurn, SubLayer.SetPT_7b, true
        ), source);
        return true;
    }
}
