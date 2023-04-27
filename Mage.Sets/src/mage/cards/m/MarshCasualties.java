
package mage.cards.m;

import java.util.Iterator;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public final class MarshCasualties extends CardImpl {

    private static final String ruleText = "Creatures target player controls get -1/-1 until end of turn. If this spell was kicked, those creatures get -2/-2 until end of turn instead";

    public MarshCasualties(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{B}{B}");

        this.color.setBlack(true);

        // Kicker {3}
        this.addAbility(new KickerAbility("{3}"));

        // Creatures target player controls get -1/-1 until end of turn. If Marsh Casualties was kicked, those creatures get -2/-2 until end of turn instead.
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(
                new MarshCasualtiesEffect(-2, -2),
                new MarshCasualtiesEffect(-1, -1),
                new LockedInCondition(KickedCondition.ONCE),
                ruleText));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private MarshCasualties(final MarshCasualties card) {
        super(card);
    }

    @Override
    public MarshCasualties copy() {
        return new MarshCasualties(this);
    }
}

class MarshCasualtiesEffect extends ContinuousEffectImpl {

    private final int power;
    private final int toughness;

    public MarshCasualtiesEffect(int power, int toughness) {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        this.power = power;
        this.toughness = toughness;
    }

    public MarshCasualtiesEffect(final MarshCasualtiesEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
    }

    @Override
    public MarshCasualtiesEffect copy() {
        return new MarshCasualtiesEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent creature : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURES, source.getFirstTarget(), game)) {
                affectedObjectList.add(new MageObjectReference(creature, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                permanent.addPower(power);
                permanent.addToughness(toughness);
            } else {
                it.remove();
            }
        }
        return true;
    }
}
