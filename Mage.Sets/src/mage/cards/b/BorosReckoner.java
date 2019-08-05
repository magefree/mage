
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LevelX2
 */
public final class BorosReckoner extends CardImpl {

    public BorosReckoner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R/W}{R/W}{R/W}");
        this.subtype.add(SubType.MINOTAUR, SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Boros Reckoner is dealt damage, it deals that much damage to any target.
        Ability ability = new DealtDamageToSourceTriggeredAbility(new BorosReckonerDealDamageEffect(), false, false, true);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // {R/W}: Boros Reckoner gains first strike until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                Zone.BATTLEFIELD, new GainAbilitySourceEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl("{R/W}")));
    }

    public BorosReckoner(final BorosReckoner card) {
        super(card);
    }

    @Override
    public BorosReckoner copy() {
        return new BorosReckoner(this);
    }
}

class BorosReckonerDealDamageEffect extends OneShotEffect {

    public BorosReckonerDealDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals that much damage to any target";
    }

    public BorosReckonerDealDamageEffect(final BorosReckonerDealDamageEffect effect) {
        super(effect);
    }

    @Override
    public BorosReckonerDealDamageEffect copy() {
        return new BorosReckonerDealDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player player = game.getPlayer(targetPointer.getFirst(game, source));
            if (player != null) {
                player.damage(amount, source.getSourceId(), game, false, true);
                return true;
            }
            Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
            if (creature != null) {
                creature.damage(amount, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
