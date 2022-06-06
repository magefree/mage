
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.LevelUpAbility;
import mage.abilities.keyword.LevelerCardBuilder;
import mage.cards.CardSetInfo;
import mage.cards.LevelerCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public final class HedronFieldPurists extends LevelerCard {

    public HedronFieldPurists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Level up {2}{W}
        this.addAbility(new LevelUpAbility(new ManaCostsImpl<>("{2}{W}")));
        // LEVEL 1-4
        // 1/4
        // If a source would deal damage to you or a creature you control, prevent 1 of that damage.
        Abilities<Ability> abilities1 = new AbilitiesImpl<>();
        abilities1.add(new SimpleStaticAbility(Zone.BATTLEFIELD, new HedronFieldPuristsEffect(1)));
        // LEVEL 5+
        // 2/5
        // If a source would deal damage to you or a creature you control, prevent 2 of that damage.
        Abilities<Ability> abilities2 = new AbilitiesImpl<>();
        abilities2.add(new SimpleStaticAbility(Zone.BATTLEFIELD, new HedronFieldPuristsEffect(2)));

        this.addAbilities(LevelerCardBuilder.construct(
                new LevelerCardBuilder.LevelAbility(1, 4, abilities1, 1, 4),
                new LevelerCardBuilder.LevelAbility(5, -1, abilities2, 2, 5)));
        setMaxLevelCounters(5);
    }

    private HedronFieldPurists(final HedronFieldPurists card) {
        super(card);
    }

    @Override
    public HedronFieldPurists copy() {
        return new HedronFieldPurists(this);
    }
}

class HedronFieldPuristsEffect extends PreventionEffectImpl {

    public HedronFieldPuristsEffect(int amount) {
        super(Duration.WhileOnBattlefield, amount, false, false);
        this.staticText = "If a source would deal damage to you or a creature you control, prevent " + amount + " of that damage";
    }

    public HedronFieldPuristsEffect(HedronFieldPuristsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGE_PLAYER
                && event.getTargetId().equals(source.getControllerId())) {
            return super.applies(event, source, game);
        }

        if (event.getType() == GameEvent.EventType.DAMAGE_PERMANENT) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.isCreature(game) && permanent.isControlledBy(source.getControllerId())) {
                return super.applies(event, source, game);
            }
        }
        return false;
    }

    @Override
    public HedronFieldPuristsEffect copy() {
        return new HedronFieldPuristsEffect(this);
    }
}
