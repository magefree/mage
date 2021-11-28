package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PoppetFactory extends CardImpl {

    public PoppetFactory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.color.setBlue(true);
        this.nightCard = true;

        // Creature tokens you control lose all abilities and have base power and toughness 3/3.
        this.addAbility(new SimpleStaticAbility(new PoppetFactoryEffect()));

        // At the beginning of your upkeep, you may transform Poppet Factory.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new TransformSourceEffect(), TargetController.YOU, true
        ));
    }

    private PoppetFactory(final PoppetFactory card) {
        super(card);
    }

    @Override
    public PoppetFactory copy() {
        return new PoppetFactory(this);
    }
}

class PoppetFactoryEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(TokenPredicate.TRUE);
    }

    PoppetFactoryEffect() {
        super(Duration.WhileOnBattlefield, Outcome.LoseAbility);
        staticText = "creature tokens you control lose all abilities and have base power and toughness 3/3";
    }

    private PoppetFactoryEffect(final PoppetFactoryEffect effect) {
        super(effect);
    }

    @Override
    public PoppetFactoryEffect copy() {
        return new PoppetFactoryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source.getSourceId(), game
        )) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    permanent.removeAllAbilities(source.getSourceId(), game);
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setValue(3);
                        permanent.getToughness().setValue(3);
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7
                || layer == Layer.AbilityAddingRemovingEffects_6;
    }
}
