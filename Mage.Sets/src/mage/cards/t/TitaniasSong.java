
package mage.cards.t;

import java.util.Iterator;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author MarcoMarin
 */
public final class TitaniasSong extends CardImpl {

    public TitaniasSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");

        // Each noncreature artifact loses all abilities and becomes an artifact creature with power and toughness each equal to its converted mana cost. If Titania's Song leaves the battlefield, this effect continues until end of turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TitaniasSongEffect(Duration.WhileOnBattlefield)));
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new TitaniasSongEffect(Duration.EndOfTurn), false));
    }

    private TitaniasSong(final TitaniasSong card) {
        super(card);
    }

    @Override
    public TitaniasSong copy() {
        return new TitaniasSong(this);
    }
}

class TitaniasSongEffect extends ContinuousEffectImpl {

    private static final FilterArtifactPermanent filter = new FilterArtifactPermanent();

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public TitaniasSongEffect(Duration duration) {
        super(duration, Outcome.BecomeCreature);
        staticText = "Each noncreature artifact loses its abilities and is an artifact creature with power and toughness each equal to its mana value";
    }

    public TitaniasSongEffect(final TitaniasSongEffect effect) {
        super(effect);
    }

    @Override
    public TitaniasSongEffect copy() {
        return new TitaniasSongEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case TypeChangingEffects_4:
                if (sublayer == SubLayer.NA) {
                    affectedObjectList.clear();
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                        if (permanent != null) {
                            affectedObjectList.add(new MageObjectReference(permanent, game));
                            permanent.addCardType(game, CardType.CREATURE);
                        }
                    }
                }
                break;
            case AbilityAddingRemovingEffects_6:
                for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
                    Permanent permanent = it.next().getPermanent(game);
                    if (permanent != null) {
                        permanent.removeAllAbilities(source.getSourceId(), game);
                    }
                }
                break;
            case PTChangingEffects_7:
                if (sublayer == SubLayer.SetPT_7b) {
                    for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
                        Permanent permanent = it.next().getPermanent(game);
                        if (permanent != null) {
                            int manaCost = permanent.getManaValue();
                            permanent.getPower().setModifiedBaseValue(manaCost);
                            permanent.getToughness().setModifiedBaseValue(manaCost);
                        }
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
        return layer == Layer.PTChangingEffects_7 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

}
