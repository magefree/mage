

package mage.cards.t;

import java.util.Iterator;
import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author Loki
 */
public final class TakenoSamuraiGeneral extends CardImpl {

    public TakenoSamuraiGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(new BushidoAbility(2));
        // Each other Samurai creature you control gets +1/+1 for each point of bushido it has.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TakenoSamuraiGeneralEffect()));
    }

    private TakenoSamuraiGeneral(final TakenoSamuraiGeneral card) {
        super(card);
    }

    @Override
    public TakenoSamuraiGeneral copy() {
        return new TakenoSamuraiGeneral(this);
    }

}

class TakenoSamuraiGeneralEffect extends ContinuousEffectImpl {
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(SubType.SAMURAI.getPredicate());
    }

    public TakenoSamuraiGeneralEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Each other Samurai creature you control gets +1/+1 for each point of bushido it has";
    }

    public TakenoSamuraiGeneralEffect(final TakenoSamuraiGeneralEffect effect) {
        super(effect);
    }

    @Override
    public TakenoSamuraiGeneralEffect copy() {
        return new TakenoSamuraiGeneralEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                if (!perm.getId().equals(source.getSourceId())) {
                    for (Ability ability : perm.getAbilities()) {
                        if (ability instanceof BushidoAbility) {
                            affectedObjectList.add(new MageObjectReference(perm, game));
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (this.affectedObjectsSet) {
            for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) { // filter may not be used again, because object can have changed filter relevant attributes but still geets boost
                Permanent permanent = it.next().getPermanent(game);
                if (permanent != null) {
                    for (Ability ability : permanent.getAbilities()) {
                        if (ability instanceof BushidoAbility) {
                            int value = ((BushidoAbility) ability).getValue(source, game, this);
                            permanent.addPower(value);
                            permanent.addToughness(value);
                        }
                    }
                } else {
                    it.remove(); // no longer on the battlefield, remove reference to object
                }
            }
        } else {
            for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, source.getControllerId(), game)) {
                if (!perm.getId().equals(source.getSourceId())) {
                    for (Ability ability : perm.getAbilities()) {
                        if (ability instanceof BushidoAbility) {
                            int value = ((BushidoAbility) ability).getValue(source, game, this);
                            perm.addPower(value);
                            perm.addToughness(value);
                        }
                    }
                }
            }
        }
        return true;
    }

}
