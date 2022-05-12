package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DenseCanopy extends CardImpl {

    public DenseCanopy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");


        // Creatures with flying can block only creatures with flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DenseCanopyCantBlockEffect()));
    }

    private DenseCanopy(final DenseCanopy card) {
        super(card);
    }

    @Override
    public DenseCanopy copy() {
        return new DenseCanopy(this);
    }
}

class DenseCanopyCantBlockEffect extends RestrictionEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public DenseCanopyCantBlockEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "creatures with flying can block only creatures with flying";
    }

    public DenseCanopyCantBlockEffect(final DenseCanopyCantBlockEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getControllerId(), source, game);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        if (attacker == null) {
            return true;
        }
        return attacker.hasAbility(FlyingAbility.getInstance(), game);
    }

    @Override
    public DenseCanopyCantBlockEffect copy() {
        return new DenseCanopyCantBlockEffect(this);
    }

}
