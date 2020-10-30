
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class LaccolithTitan extends CardImpl {

    public LaccolithTitan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever Laccolith Grunt becomes blocked, you may have it deal damage equal to its power to target creature. If you do, Laccolith Grunt assigns no combat damage this turn.
        Ability ability = new BecomesBlockedSourceTriggeredAbility(new LaccolithEffect().setText("you may have it deal damage equal to its power to target creature"), true);
        ability.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn, true));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public LaccolithTitan(final LaccolithTitan card) {
        super(card);
    }

    @Override
    public LaccolithTitan copy() {
        return new LaccolithTitan(this);
    }
    
    class LaccolithEffect extends OneShotEffect {
        public LaccolithEffect() {
            super(Outcome.Damage);
            staticText = "{this} deals damage equal to its power to target creature";
        }
    
        public LaccolithEffect(final LaccolithEffect effect) {
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
                permanent.damage(damage, sourcePermanent.getId(), game, false, true);
                return true;
            }
            return false;
        }
    
        @Override
        public LaccolithEffect copy() {
            return new LaccolithEffect(this);
        }
    
    }
}
