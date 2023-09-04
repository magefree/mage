
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class MagneticTheft extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Equipment");

    static {
        filter.add(SubType.EQUIPMENT.getPredicate());
    }

    public MagneticTheft(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Attach target Equipment to target creature.
        this.getSpellAbility().addEffect(new EquipEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MagneticTheft(final MagneticTheft card) {
        super(card);
    }

    @Override
    public MagneticTheft copy() {
        return new MagneticTheft(this);
    }
}

class EquipEffect extends OneShotEffect {

    public EquipEffect() {
        super(Outcome.BoostCreature);
        staticText = "Attach target Equipment to target creature";
    }

    private EquipEffect(final EquipEffect effect) {
        super(effect);
    }

    @Override
    public EquipEffect copy() {
        return new EquipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getFirstTarget());
        Permanent creature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (creature != null && equipment != null) {
            return creature.addAttachment(equipment.getId(), source, game);
        }
        return false;
    }
}