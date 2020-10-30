package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePlayerOrPlaneswalker;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;

import java.io.ObjectStreamException;
import java.util.UUID;

/**
 * @author maurer.it_at_gmail.com
 */
public final class ArcTrail extends CardImpl {

    public ArcTrail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Arc Trail deals 2 damage to any target and 1 damage to another any target
        FilterCreaturePlayerOrPlaneswalker filter1 = new FilterCreaturePlayerOrPlaneswalker("creature, player or planeswalker to deal 2 damage");
        TargetAnyTarget target1 = new TargetAnyTarget(1, 1, filter1);
        target1.setTargetTag(1);
        this.getSpellAbility().addTarget(target1);

        FilterCreaturePlayerOrPlaneswalker filter2 = new FilterCreaturePlayerOrPlaneswalker("another creature, player or planeswalker to deal 1 damage");
        AnotherTargetPredicate predicate = new AnotherTargetPredicate(2);
        filter2.getCreatureFilter().add(predicate);
        filter2.getPlayerFilter().add(predicate);
        TargetAnyTarget target2 = new TargetAnyTarget(1, 1, filter2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);

        this.getSpellAbility().addEffect(ArcTrailEffect.getInstance());
    }

    public ArcTrail(final ArcTrail card) {
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
                applied |= (permanent.damage(damage, source.getSourceId(), game, false, true) > 0);
            }
            Player player = game.getPlayer(target.getFirstTarget());
            if (player != null) {
                applied |= (player.damage(damage, source.getSourceId(), game) > 0);
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
