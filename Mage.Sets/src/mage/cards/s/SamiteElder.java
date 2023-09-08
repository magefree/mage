
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LoneFox
 */
public final class SamiteElder extends CardImpl {

    public SamiteElder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Creatures you control gain protection from the colors of target permanent you control until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SamiteElderEffect(), new TapSourceCost());
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    private SamiteElder(final SamiteElder card) {
        super(card);
    }

    @Override
    public SamiteElder copy() {
        return new SamiteElder(this);
    }
}

class SamiteElderEffect extends OneShotEffect {

    public SamiteElderEffect() {
        super(Outcome.Protect);
        staticText = "Creatures you control gain protection from the colors of target permanent you control until end of turn";
    }

    private SamiteElderEffect(final SamiteElderEffect effect) {
        super(effect);
    }

    public SamiteElderEffect copy() {
        return new SamiteElderEffect(this);
    }

    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        if(target != null) {
            for(ObjectColor color : target.getColor(game).getColors()) {
                FilterCard filter = new FilterCard(color.getDescription());
                filter.add(new ColorPredicate(color));
                game.addEffect(new GainAbilityControlledEffect(new ProtectionAbility(filter),
                    Duration.EndOfTurn, new FilterControlledCreaturePermanent()), source);
            }
            return true;
        }
        return false;
    }
}
