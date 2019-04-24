
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.combat.CantAttackIfDefenderControlsPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author L_J
 */
public final class BrandedBrawlers extends CardImpl {

    static final private FilterLandPermanent filter = new FilterLandPermanent("an untapped land");
    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }
    
    final static private String rule = "{this} can't block if you control an untapped land";

    public BrandedBrawlers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Branded Brawlers can't attack if defending player controls an untapped land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackIfDefenderControlsPermanent(filter)));

        // Branded Brawlers can't block if you control an untapped land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BrandedBrawlersCantBlockEffect(filter)));
    }

    public BrandedBrawlers(final BrandedBrawlers card) {
        super(card);
    }

    @Override
    public BrandedBrawlers copy() {
        return new BrandedBrawlers(this);
    }
}

class BrandedBrawlersCantBlockEffect extends RestrictionEffect {

    private final FilterPermanent filter;

    public BrandedBrawlersCantBlockEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield);
        this.filter = filter;
        staticText = new StringBuilder("{this} can't attack if you control ").append(filter.getMessage()).toString();
    }

    public BrandedBrawlersCantBlockEffect(final BrandedBrawlersCantBlockEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        Player player = game.getPlayer(blocker.getControllerId());
        if (player != null) {
            if (game.getBattlefield().countAll(filter, player.getId(), game) > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public BrandedBrawlersCantBlockEffect copy() {
        return new BrandedBrawlersCantBlockEffect(this);
    }

}
