
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class FiredrinkerSatyr extends CardImpl {

    public FiredrinkerSatyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Firedrinker Satyr is dealt damage, it deals that much damage to you.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new FiredrinkerSatyrDealDamageEffect(), false, false, true));
        // {1}{R}: Firedrinker Satyr gets +1/+0 until end of turn and deals 1 damage to you.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl("{1}{R}"));
        Effect effect = new DamageControllerEffect(1);
        effect.setText("and deals 1 damage to you");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public FiredrinkerSatyr(final FiredrinkerSatyr card) {
        super(card);
    }

    @Override
    public FiredrinkerSatyr copy() {
        return new FiredrinkerSatyr(this);
    }
}

class FiredrinkerSatyrDealDamageEffect extends OneShotEffect {

    public FiredrinkerSatyrDealDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals that much damage to you";
    }

    public FiredrinkerSatyrDealDamageEffect(final FiredrinkerSatyrDealDamageEffect effect) {
        super(effect);
    }

    @Override
    public FiredrinkerSatyrDealDamageEffect copy() {
        return new FiredrinkerSatyrDealDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.damage(amount, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
