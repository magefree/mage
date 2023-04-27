
package mage.cards.x;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author MarcoMarin
 */
public final class XenicPoltergeist extends CardImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent();

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public XenicPoltergeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Until your next upkeep, target noncreature artifact becomes an artifact creature with power and toughness each equal to its converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new XenicPoltergeistEffect(), new TapSourceCost());
        ability.addTarget(new TargetArtifactPermanent(filter));
        this.addAbility(ability);

    }

    private XenicPoltergeist(final XenicPoltergeist card) {
        super(card);
    }

    @Override
    public XenicPoltergeist copy() {
        return new XenicPoltergeist(this);
    }
}

class XenicPoltergeistEffect extends ContinuousEffectImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent();

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public XenicPoltergeistEffect() {
        super(Duration.Custom, Outcome.BecomeCreature);
        staticText = "Until your next upkeep, target noncreature artifact becomes an artifact creature with power and toughness each equal to its mana value";
    }

    public XenicPoltergeistEffect(final XenicPoltergeistEffect effect) {
        super(effect);
    }

    @Override
    public XenicPoltergeistEffect copy() {
        return new XenicPoltergeistEffect(this);
    }

    @Override
    public boolean isInactive(Ability source, Game game) {
        if (game.getPhase().getStep().getType() == PhaseStep.UPKEEP) {
            if (game.isActivePlayer(source.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    UUID permanentId = targetPointer.getFirst(game, source);
                    Permanent permanent = game.getPermanentOrLKIBattlefield(permanentId);
                    if (permanent != null) {
                        if (!permanent.isArtifact(game)) {
                            permanent.addCardType(game, CardType.ARTIFACT);
                        }
                        if (!permanent.isCreature(game)) {
                            permanent.addCardType(game, CardType.CREATURE);
                        }
                    }
                }
                break;

            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    UUID permanentId = targetPointer.getFirst(game, source);
                    Permanent permanent = game.getPermanentOrLKIBattlefield(permanentId);
                    if (permanent != null) {
                        int manaCost = permanent.getManaValue();
                        permanent.getPower().setModifiedBaseValue(manaCost);
                        permanent.getToughness().setModifiedBaseValue(manaCost);
                    }
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
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.TypeChangingEffects_4;
    }

}
