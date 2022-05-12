

package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class RelentlessRats extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new NamePredicate("Relentless Rats"));
    }

    public RelentlessRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}{B}");
        this.subtype.add(SubType.RAT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Relentless Rats gets +1/+1 for each other creature on the battlefield named Relentless Rats.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RelentlessRatsEffect()));

        // A deck can have any number of cards named Relentless Rats.
        this.getSpellAbility().addEffect(new InfoEffect("A deck can have any number of cards named Relentless Rats."));
    }

    private RelentlessRats(final RelentlessRats card) {
        super(card);
    }

    @Override
    public RelentlessRats copy() {
        return new RelentlessRats(this);
    }

    static class RelentlessRatsEffect extends ContinuousEffectImpl {

        public RelentlessRatsEffect() {
            super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
            staticText = "{this} gets +1/+1 for each other creature on the battlefield named Relentless Rats";
        }

        public RelentlessRatsEffect(final RelentlessRatsEffect effect) {
            super(effect);
        }

        @Override
        public RelentlessRatsEffect copy() {
            return new RelentlessRatsEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            int count = game.getBattlefield().count(filter, source.getControllerId(), source, game) - 1;
            if (count > 0) {
                Permanent target = game.getPermanent(source.getSourceId());
                if (target != null) {
                    target.addPower(count);
                    target.addToughness(count);
                    return true;
                }
            }
            return false;
        }

    }
}
