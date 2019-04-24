
package mage.cards.b;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Dilnu
 */
public final class Brightflame extends CardImpl {

    public Brightflame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}{R}{W}{W}");

        // Radiance - Brightflame deals X damage to target creature and each other creature that shares a color with it. You gain life equal to the damage dealt this way.
        this.getSpellAbility().addEffect(new BrightflameEffect(new ManacostVariableValue()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().setAbilityWord(AbilityWord.RADIANCE);
    }

    public Brightflame(final Brightflame card) {
        super(card);
    }

    @Override
    public Brightflame copy() {
        return new Brightflame(this);
    }
}

class BrightflameEffect extends OneShotEffect {

    static final FilterPermanent filter = new FilterPermanent("creature");
    protected DynamicValue amount;

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    BrightflameEffect(DynamicValue amount) {
        super(Outcome.Damage);
        this.amount = amount;
        staticText = "{this} deals X damage to target creature and each other creature that shares a color with it. You gain life equal to the damage dealt this way.";
    }

    BrightflameEffect(final BrightflameEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(targetPointer.getFirst(game, source));
        int damageDealt = 0;
        if (target != null) {
            ObjectColor color = target.getColor(game);
            damageDealt += target.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, true);
            for (Permanent p : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (!target.getId().equals(p.getId()) && p.getColor(game).shares(color)) {
                    damageDealt += p.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, true);
                }
            }
            
            Player you = game.getPlayer(source.getControllerId());
            if (you != null && damageDealt > 0) {
                you.gainLife(damageDealt, game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public BrightflameEffect copy() {
        return new BrightflameEffect(this);
    }
}