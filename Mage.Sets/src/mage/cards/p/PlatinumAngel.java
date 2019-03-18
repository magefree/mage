

package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class PlatinumAngel extends CardImpl {

    public PlatinumAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{7}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PlatinumAngelEffect()));
    }

    public PlatinumAngel(final PlatinumAngel card) {
        super(card);
    }

    @Override
    public PlatinumAngel copy() {
        return new PlatinumAngel(this);
    }

    static class PlatinumAngelEffect extends ContinuousRuleModifyingEffectImpl {

        public PlatinumAngelEffect() {
            super(Duration.WhileOnBattlefield, Outcome.Benefit, false, false);
            staticText = "You can't lose the game and your opponents can't win the game";
        }

        public PlatinumAngelEffect(final PlatinumAngelEffect effect) {
            super(effect);
        }

        @Override
        public PlatinumAngelEffect copy() {
            return new PlatinumAngelEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return true;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
            if ((event.getType() == EventType.WINS && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) ||
                (event.getType() == EventType.LOSES && event.getPlayerId().equals(source.getControllerId()))) {
                return true;
            }
            return false;
        }

    }

}
