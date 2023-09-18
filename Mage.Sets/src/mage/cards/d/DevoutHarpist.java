
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Plopman
 */
public final class DevoutHarpist extends CardImpl {
    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Aura attached to a creature");
    static
    {
        filter.add(SubType.AURA.getPredicate());
        filter.add(new DevoutHarpistPredicate());
    }
    public DevoutHarpist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Destroy target Aura attached to a creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

    }

    private DevoutHarpist(final DevoutHarpist card) {
        super(card);
    }

    @Override
    public DevoutHarpist copy() {
        return new DevoutHarpist(this);
    }

}

class DevoutHarpistPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent attachment = input.getObject();
        if (attachment != null) {
            Permanent permanent = game.getPermanent(attachment.getAttachedTo());
            if (permanent != null && permanent.isCreature(game)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return "Attached to a creature";
    }
}
