
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class RecumbentBliss extends CardImpl {

    public RecumbentBliss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.AURA);

        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RecumbentBlissEffect()));
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(1), TargetController.YOU, true));
    }

    public RecumbentBliss(final RecumbentBliss card) {
        super(card);
    }

    @Override
    public RecumbentBliss copy() {
        return new RecumbentBliss(this);
    }
}

class RecumbentBlissEffect extends RestrictionEffect {

    public RecumbentBlissEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Enchanted creature can't attack or block";
    }

    public RecumbentBlissEffect(final RecumbentBlissEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.getAttachments().contains((source.getSourceId()))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public RecumbentBlissEffect copy() {
        return new RecumbentBlissEffect(this);
    }

}

