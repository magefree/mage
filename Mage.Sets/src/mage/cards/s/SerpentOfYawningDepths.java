package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SerpentOfYawningDepths extends CardImpl {

    public SerpentOfYawningDepths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Krakens, Leviathans, Octopuses, and Serpents you control can't be blocked except by Krakens, Leviathans, Octopuses, and Serpents.
        this.addAbility(new SimpleStaticAbility(new SerpentOfYawningDepthsEffect()));
    }

    private SerpentOfYawningDepths(final SerpentOfYawningDepths card) {
        super(card);
    }

    @Override
    public SerpentOfYawningDepths copy() {
        return new SerpentOfYawningDepths(this);
    }
}

class SerpentOfYawningDepthsEffect extends RestrictionEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(Predicates.or(
                SubType.KRAKEN.getPredicate(),
                SubType.LEVIATHAN.getPredicate(),
                SubType.OCTOPUS.getPredicate(),
                SubType.SERPENT.getPredicate()
        ));
    }

    SerpentOfYawningDepthsEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "Krakens, Leviathans, Octopuses, and Serpents you control " +
                "can't be blocked except by Krakens, Leviathans, Octopuses, and Serpents.";
    }

    private SerpentOfYawningDepthsEffect(SerpentOfYawningDepthsEffect effect) {
        super(effect);
    }

    @Override
    public SerpentOfYawningDepthsEffect copy() {
        return new SerpentOfYawningDepthsEffect(this);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return filter.match(blocker, game);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isControlledBy(source.getControllerId()) && filter.match(permanent, game);
    }
}
