package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class WojekEmbermage extends CardImpl {

    public WojekEmbermage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Radiance - {tap}: Wojek Embermage deals 1 damage to target creature and each other creature that shares a color with it.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WojekEmbermageEffect(), new TapSourceCost());
        ability.setAbilityWord(AbilityWord.RADIANCE);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private WojekEmbermage(final WojekEmbermage card) {
        super(card);
    }

    @Override
    public WojekEmbermage copy() {
        return new WojekEmbermage(this);
    }
}

class WojekEmbermageEffect extends OneShotEffect {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent();

    WojekEmbermageEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 1 damage to target creature and each other creature that shares a color with it";
    }

    private WojekEmbermageEffect(final WojekEmbermageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (target != null) {
            ObjectColor targetColor = target.getColor(game);
            for (Permanent permanent : game.getBattlefield().getActivePermanents(FILTER, source.getControllerId(), game)) {
                if (target.getId().equals(permanent.getId()) || permanent.getColor(game).shares(targetColor)) {
                    permanent.damage(1, source.getSourceId(), source, game, false, true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public WojekEmbermageEffect copy() {
        return new WojekEmbermageEffect(this);
    }
}
