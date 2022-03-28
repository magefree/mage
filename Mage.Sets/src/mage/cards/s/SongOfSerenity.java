package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class SongOfSerenity extends CardImpl {

    public SongOfSerenity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        // Creatures that are enchanted can't attack or block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SongOfSerenityRestrictionEffect()));

    }

    private SongOfSerenity(final SongOfSerenity card) {
        super(card);
    }

    @Override
    public SongOfSerenity copy() {
        return new SongOfSerenity(this);
    }
}

class SongOfSerenityRestrictionEffect extends RestrictionEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(EnchantedPredicate.instance);
    }

    public SongOfSerenityRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures that are enchanted can't attack or block";
    }

    public SongOfSerenityRestrictionEffect(final SongOfSerenityRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public SongOfSerenityRestrictionEffect copy() {
        return new SongOfSerenityRestrictionEffect(this);
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return filter.match(permanent, source.getControllerId(), source, game);
    }
}
