package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChallengerTroll extends CardImpl {

    public ChallengerTroll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.TROLL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Each creature you control with power 4 or greater can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(new ChallengerTrollEffect()));
    }

    private ChallengerTroll(final ChallengerTroll card) {
        super(card);
    }

    @Override
    public ChallengerTroll copy() {
        return new ChallengerTroll(this);
    }
}

class ChallengerTrollEffect extends ContinuousEffectImpl {

    ChallengerTrollEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Each creature you control with power 4 or greater can't be blocked by more than one creature.";
    }

    private ChallengerTrollEffect(final ChallengerTrollEffect effect) {
        super(effect);
    }

    @Override
    public ChallengerTrollEffect copy() {
        return new ChallengerTrollEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (layer != Layer.RulesEffects) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (permanent != null && permanent.isControlledBy(source.getControllerId())
                    && permanent.isCreature(game) && permanent.getPower().getValue() >= 4) {
                permanent.setMaxBlockedBy(1);
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
