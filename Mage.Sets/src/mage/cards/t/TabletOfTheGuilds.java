
package mage.cards.t;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.Locale;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TabletOfTheGuilds extends CardImpl {

    public TabletOfTheGuilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // As Tablet of the Guilds enters the battlefield, choose two colors.
        this.addAbility(new AsEntersBattlefieldAbility(new TabletOfTheGuildsEntersBattlefieldEffect()));

        // Whenever you cast a spell, if it's at least one of the chosen colors, you gain 1 life for each of the chosen colors it is.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new TabletOfTheGuildsGainLifeEffect(),
                StaticFilters.FILTER_SPELL_A,
                false,
                SetTargetPointer.SPELL
        ));
    }

    private TabletOfTheGuilds(final TabletOfTheGuilds card) {
        super(card);
    }

    @Override
    public TabletOfTheGuilds copy() {
        return new TabletOfTheGuilds(this);
    }
}

class TabletOfTheGuildsEntersBattlefieldEffect extends OneShotEffect {

    public TabletOfTheGuildsEntersBattlefieldEffect() {
        super(Outcome.BoostCreature);
        staticText = "choose two colors";
    }

    private TabletOfTheGuildsEntersBattlefieldEffect(final TabletOfTheGuildsEntersBattlefieldEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (player != null && permanent != null) {
            String colors;
            ChoiceColor colorChoice = new ChoiceColor();
            colorChoice.setMessage("Choose the first color");
            if (!player.choose(Outcome.GainLife, colorChoice, game)) {
                return false;
            }
            game.getState().setValue(permanent.getId() + "_color1", colorChoice.getColor().toString());
            colors = colorChoice.getChoice().toLowerCase(Locale.ENGLISH) + " and ";
            colorChoice.getChoices().remove(colorChoice.getChoice());
            colorChoice.setMessage("Choose the second color");
            if (!player.choose(Outcome.GainLife, colorChoice, game) && player.canRespond()) {
                return false;
            }
            game.getState().setValue(permanent.getId() + "_color2", colorChoice.getColor().toString());
            colors = colors + colorChoice.getChoice().toLowerCase(Locale.ENGLISH);
            game.informPlayers(permanent.getName() + ": " + player.getLogName() + " has chosen " + colors);
            return true;
        }
        return false;
    }

    @Override
    public TabletOfTheGuildsEntersBattlefieldEffect copy() {
        return new TabletOfTheGuildsEntersBattlefieldEffect(this);
    }

}

class TabletOfTheGuildsGainLifeEffect extends OneShotEffect {

    public TabletOfTheGuildsGainLifeEffect() {
        super(Outcome.Neutral);
        staticText = "if it's at least one of the chosen colors, you gain 1 life for each of the chosen colors it is";
    }

    private TabletOfTheGuildsGainLifeEffect(final TabletOfTheGuildsGainLifeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
            if (spell != null) {
                ObjectColor color1 = new ObjectColor((String) game.getState().getValue(source.getSourceId() + "_color1"));
                ObjectColor color2 = new ObjectColor((String) game.getState().getValue(source.getSourceId() + "_color2"));
                int amount = 0;
                if (spell.getColor(game).contains(color1)) {
                    ++amount;
                }
                if (spell.getColor(game).contains(color2)) {
                    ++amount;
                }
                if (amount > 0) {
                    you.gainLife(amount, game, source);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TabletOfTheGuildsGainLifeEffect copy() {
        return new TabletOfTheGuildsGainLifeEffect(this);
    }
}
