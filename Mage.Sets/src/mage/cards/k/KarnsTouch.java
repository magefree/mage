
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author TheElk801
 */
public final class KarnsTouch extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent("noncreature artifact");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public KarnsTouch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{U}");

        // Target noncreature artifact becomes an artifact creature with power and toughness each equal to its converted mana cost until end of turn.
        this.getSpellAbility().addEffect(new KarnsTouchEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private KarnsTouch(final KarnsTouch card) {
        super(card);
    }

    @Override
    public KarnsTouch copy() {
        return new KarnsTouch(this);
    }
}

class KarnsTouchEffect extends ContinuousEffectImpl {

    public KarnsTouchEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        staticText = "Target noncreature artifact becomes an artifact creature with power and toughness each equal to its mana value until end of turn";
    }

    public KarnsTouchEffect(final KarnsTouchEffect effect) {
        super(effect);
    }

    @Override
    public KarnsTouchEffect copy() {
        return new KarnsTouchEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent artifact = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (artifact == null) {
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    if (!artifact.isArtifact(game)) {
                        artifact.addCardType(game, CardType.ARTIFACT);
                    }
                    if (!artifact.isCreature(game)) {
                        artifact.addCardType(game, CardType.CREATURE);
                    }
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    int cmc = artifact.getManaValue();
                    artifact.getPower().setModifiedBaseValue(cmc);
                    artifact.getToughness().setModifiedBaseValue(cmc);
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
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }
}
