package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Chaosphere extends CardImpl {

    static final private FilterCreaturePermanent filterCreature = new FilterCreaturePermanent();
    static final private String rule = "Creatures without flying have reach.";

    static {
        filterCreature.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public Chaosphere(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");

        addSuperType(SuperType.WORLD);

        // Creatures with flying can block only creatures with flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChaosphereEffect()));

        // Creatures without flying have reach.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(ReachAbility.getInstance(), Duration.WhileOnBattlefield, filterCreature, rule)));

    }

    private Chaosphere(final Chaosphere card) {
        super(card);
    }

    @Override
    public Chaosphere copy() {
        return new Chaosphere(this);
    }
}

class ChaosphereEffect extends RestrictionEffect {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public ChaosphereEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "creatures with flying can block only creatures with flying";
    }

    public ChaosphereEffect(final ChaosphereEffect effect) {
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
    public ChaosphereEffect copy() {
        return new ChaosphereEffect(this);
    }

}
