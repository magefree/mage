package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SpikeshotElder extends CardImpl {

    public SpikeshotElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SpikeshotElderEffect(), new ManaCostsImpl<>("{1}{R}{R}"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private SpikeshotElder(final SpikeshotElder card) {
        super(card);
    }

    @Override
    public SpikeshotElder copy() {
        return new SpikeshotElder(this);
    }
}

class SpikeshotElderEffect extends OneShotEffect {
    public SpikeshotElderEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage equal to its power to any target";
    }

    public SpikeshotElderEffect(final SpikeshotElderEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent == null) {
            sourcePermanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (sourcePermanent == null) {
            return false;
        }

        int damage = sourcePermanent.getPower().getValue();

        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(damage, sourcePermanent.getId(), source, game, false, true);
            return true;
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(damage, sourcePermanent.getId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public SpikeshotElderEffect copy() {
        return new SpikeshotElderEffect(this);
    }

}