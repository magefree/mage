
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.PreventionEffectData;
import mage.abilities.effects.common.PreventDamageToControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class VengefulArchon extends CardImpl {

    public VengefulArchon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{W}");
        this.subtype.add(SubType.ARCHON);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {X}: Prevent the next X damage that would be dealt to you this turn. If damage is prevented this way, Vengeful Archon deals that much damage to target player.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VengefulArchonEffect(), new ManaCostsImpl<>("{X}"));
        ability.addTarget(new TargetPlayerOrPlaneswalker());
        this.addAbility(ability);
    }

    private VengefulArchon(final VengefulArchon card) {
        super(card);
    }

    @Override
    public VengefulArchon copy() {
        return new VengefulArchon(this);
    }

}

class VengefulArchonEffect extends PreventDamageToControllerEffect {

    public VengefulArchonEffect() {
        super(Duration.EndOfTurn, false, true, ManacostVariableValue.REGULAR);
        staticText = "Prevent the next X damage that would be dealt to you this turn. If damage is prevented this way, {this} deals that much damage to target player or planeswalker";
    }

    public VengefulArchonEffect(final VengefulArchonEffect effect) {
        super(effect);
    }

    @Override
    public VengefulArchonEffect copy() {
        return new VengefulArchonEffect(this);
    }

    @Override
    protected PreventionEffectData preventDamageAction(GameEvent event, Ability source, Game game) {
        PreventionEffectData preventionEffectData = super.preventDamageAction(event, source, game);
        int damage = preventionEffectData.getPreventedDamage();
        if (damage > 0) {
            game.damagePlayerOrPermanent(source.getFirstTarget(), damage, source.getSourceId(), source, game, false, true);
        }
        return preventionEffectData;
    }

}
