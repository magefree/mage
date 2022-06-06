
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
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
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class KillSuitCultist extends CardImpl {

    public KillSuitCultist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kill-Suit Cultist attacks each turn if able.
        this.addAbility(new AttacksEachCombatStaticAbility());
        // {B}, Sacrifice Kill-Suit Cultist: The next time damage would be dealt to target creature this turn, destroy that creature instead.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new KillSuitCultistEffect(), new ManaCostsImpl<>("{B}"));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KillSuitCultist(final KillSuitCultist card) {
        super(card);
    }

    @Override
    public KillSuitCultist copy() {
        return new KillSuitCultist(this);
    }
}

class KillSuitCultistEffect extends ReplacementEffectImpl {

    public KillSuitCultistEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "The next time damage would be dealt to target creature this turn, destroy that creature instead";
    }

    public KillSuitCultistEffect(final KillSuitCultistEffect effect) {
        super(effect);
    }

    @Override
    public KillSuitCultistEffect copy() {
        return new KillSuitCultistEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getTargetId().equals(targetPointer.getFirst(game, source));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if(permanent != null) {
            permanent.destroy(source, game, false);
            return true;
        }
        return false;
    }

}
