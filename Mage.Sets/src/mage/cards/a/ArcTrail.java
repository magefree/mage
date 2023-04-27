package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterAnyTarget;
import mage.filter.common.FilterPermanentOrPlayer;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetPermanentOrPlayer;

import java.io.ObjectStreamException;
import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class ArcTrail extends CardImpl {

    private static final FilterPermanentOrPlayer filter1 = new FilterAnyTarget("creature, player or planeswalker to deal 2 damage");
    private static final FilterPermanentOrPlayer filter2 = new FilterAnyTarget("another creature, player or planeswalker to deal 1 damage");

    static {
        filter2.getPermanentFilter().add(new AnotherTargetPredicate(2));
        filter2.getPlayerFilter().add(new AnotherTargetPredicate(2));
    }

    public ArcTrail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Arc Trail deals 2 damage to any target and 1 damage to another any target
        this.getSpellAbility().addEffect(ArcTrailEffect.getInstance());
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(filter1).setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanentOrPlayer(filter2).setTargetTag(2));
    }

    private ArcTrail(final ArcTrail card) {
        super(card);
    }

    @Override
    public ArcTrail copy() {
        return new ArcTrail(this);
    }

}

class ArcTrailEffect extends OneShotEffect {

    private static final ArcTrailEffect instance = new ArcTrailEffect();

    private Object readResolve() throws ObjectStreamException {
        return instance;
    }

    public static ArcTrailEffect getInstance() {
        return instance;
    }

    private ArcTrailEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to any target and 1 damage to another target";
    }

    @Override
    public boolean apply(Game game, Ability source) {

        boolean applied = false;
        boolean twoDamageDone = false;
        int damage = 2;

        for (Target target : source.getTargets()) {
            Permanent permanent = game.getPermanent(target.getFirstTarget());

            if (twoDamageDone) {
                damage = 1;
            }

            if (permanent != null) {
                applied |= (permanent.damage(damage, source.getSourceId(), source, game, false, true) > 0);
            }
            Player player = game.getPlayer(target.getFirstTarget());
            if (player != null) {
                applied |= (player.damage(damage, source.getSourceId(), source, game) > 0);
            }

            twoDamageDone = true;
        }
        return applied;
    }

    @Override
    public Effect copy() {
        return instance;
    }

}
