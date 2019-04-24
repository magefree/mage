
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlankingAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class SidarKondoOfJamuraa extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public SidarKondoOfJamuraa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flanking
        this.addAbility(new FlankingAbility());

        // Creatures your opponents control without flying or reach can't block creatures with power 2 or less.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SidarKondoOfJamuraaCantBlockCreaturesSourceEffect(filter)));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    public SidarKondoOfJamuraa(final SidarKondoOfJamuraa card) {
        super(card);
    }

    @Override
    public SidarKondoOfJamuraa copy() {
        return new SidarKondoOfJamuraa(this);
    }
}

class SidarKondoOfJamuraaCantBlockCreaturesSourceEffect extends RestrictionEffect {

    private final FilterCreaturePermanent filter;

    public SidarKondoOfJamuraaCantBlockCreaturesSourceEffect(FilterCreaturePermanent filter) {
        this(filter, Duration.WhileOnBattlefield);
    }

    public SidarKondoOfJamuraaCantBlockCreaturesSourceEffect(FilterCreaturePermanent filter, Duration duration) {
        super(duration);
        this.filter = filter;
        staticText = "Creatures your opponents control without flying or reach can't block " + filter.getMessage();
    }

    public SidarKondoOfJamuraaCantBlockCreaturesSourceEffect(SidarKondoOfJamuraaCantBlockCreaturesSourceEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (permanent.hasAbility(FlyingAbility.getInstance().getId(), game)
                || permanent.hasAbility(ReachAbility.getInstance().getId(), game)) {
            return false;
        }
        return game.getOpponents(source.getControllerId()).contains(permanent.getControllerId());
    }
    
    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return !filter.match(attacker, source.getSourceId(), source.getControllerId(), game);
    }

    @Override
    public ContinuousEffect copy() {
        return new SidarKondoOfJamuraaCantBlockCreaturesSourceEffect(this);
    }
}
