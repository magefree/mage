package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.combat.CantAttackIfDefenderControlsPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author L_J
 */
public final class VeteranBrawlers extends CardImpl {

    static final private FilterLandPermanent filter = new FilterLandPermanent("an untapped land");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public VeteranBrawlers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Veteran Brawlers can't attack if defending player controls an untapped land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackIfDefenderControlsPermanent(filter)));

        // Veteran Brawlers can't block if you control an untapped land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VeteranBrawlersCantBlockEffect(filter)));
    }

    private VeteranBrawlers(final VeteranBrawlers card) {
        super(card);
    }

    @Override
    public VeteranBrawlers copy() {
        return new VeteranBrawlers(this);
    }
}

class VeteranBrawlersCantBlockEffect extends RestrictionEffect {

    private final FilterPermanent filter;

    public VeteranBrawlersCantBlockEffect(FilterPermanent filter) {
        super(Duration.WhileOnBattlefield);
        this.filter = filter;
        staticText = "{this} can't block if you control " + filter.getMessage();
    }

    public VeteranBrawlersCantBlockEffect(final VeteranBrawlersCantBlockEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        Player player = game.getPlayer(blocker.getControllerId());
        if (player != null) {
            return game.getBattlefield().countAll(filter, player.getId(), game) <= 0;
        }
        return true;
    }

    @Override
    public VeteranBrawlersCantBlockEffect copy() {
        return new VeteranBrawlersCantBlockEffect(this);
    }

}
