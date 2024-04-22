
package mage.cards.d;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.watchers.common.SpellsCastWatcher;

/**
 *
 * @author jeffwadsworth
 */
public final class DreamThief extends CardImpl {

    private static final String rule = "draw a card if you've cast another blue spell this turn";

    public DreamThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Dream Thief enters the battlefield, draw a card if you've cast another blue spell this turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), new CastBlueSpellThisTurnCondition(), rule)));

    }

    private DreamThief(final DreamThief card) {
        super(card);
    }

    @Override
    public DreamThief copy() {
        return new DreamThief(this);
    }
}

class CastBlueSpellThisTurnCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        if (watcher != null) {
            List<Spell> spells = watcher.getSpellsCastThisTurn(source.getControllerId());
            if (spells != null) {
                for (Spell spell : spells) {
                    if (!spell.getSourceId().equals(source.getSourceId()) && spell.getColor(game).isBlue()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
