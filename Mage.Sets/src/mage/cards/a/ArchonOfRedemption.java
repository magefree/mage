package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author Loki
 */
public final class ArchonOfRedemption extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public ArchonOfRedemption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.ARCHON);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever Archon of Redemption or another creature with flying enters the battlefield under your control, you may gain life equal to that creature's power.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new ArchonOfRedemptionEffect(), filter, true, SetTargetPointer.PERMANENT, true
        ));
    }

    private ArchonOfRedemption(final ArchonOfRedemption card) {
        super(card);
    }

    @Override
    public ArchonOfRedemption copy() {
        return new ArchonOfRedemption(this);
    }
}

class ArchonOfRedemptionEffect extends OneShotEffect {

    ArchonOfRedemptionEffect() {
        super(Outcome.Benefit);
        staticText = "gain life equal to that creature's power";
    }

    private ArchonOfRedemptionEffect(final ArchonOfRedemptionEffect effect) {
        super(effect);
    }

    @Override
    public ArchonOfRedemptionEffect copy() {
        return new ArchonOfRedemptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (player == null || permanent == null) {
            return false;
        }
        return player.gainLife(permanent.getPower().getValue(), game, source) > 0;
    }
}
