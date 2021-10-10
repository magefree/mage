
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
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
 * @author fireshoes
 */
public final class SavaenElves extends CardImpl {
    
    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Auras attached to a land");
    
    static {
        filter.add(SubType.AURA.getPredicate());
        filter.add(new SavaenElvesPredicate());
    }

    public SavaenElves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {G}{G}, {tap}: Destroy target Aura attached to a land.
        Effect effect = new DestroyTargetEffect();
        effect.setText("Destroy target Aura attached to a land");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{G}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SavaenElves(final SavaenElves card) {
        super(card);
    }

    @Override
    public SavaenElves copy() {
        return new SavaenElves(this);
    }
}

class SavaenElvesPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent attachment = input.getObject();
        if (attachment != null) {
            Permanent permanent = game.getPermanent(attachment.getAttachedTo());
            if (permanent != null && permanent.isLand(game)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        return "Attached to a land";
    }
}
