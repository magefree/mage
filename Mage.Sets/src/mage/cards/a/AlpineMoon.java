package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlpineMoon extends CardImpl {

    public AlpineMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        // As Alpine Moon enters the battlefield, choose a nonbasic land card name.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.NONBASIC_LAND_NAME)));

        // Lands your opponents control with the chosen name lose all land types and abilities, and they gain "{T}: Add one mana of any color."
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AlpineMoonEffect()));
    }

    private AlpineMoon(final AlpineMoon card) {
        super(card);
    }

    @Override
    public AlpineMoon copy() {
        return new AlpineMoon(this);
    }
}

class AlpineMoonEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterLandPermanent();

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
    }

    public AlpineMoonEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.staticText = "lands your opponents control with the chosen name "
                + "lose all land types and abilities, "
                + "and they gain \"{T}: Add one mana of any color.\"";
    }

    private AlpineMoonEffect(final AlpineMoonEffect effect) {
        super(effect);
    }

    @Override
    public AlpineMoonEffect copy() {
        return new AlpineMoonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (cardName == null) {
            return false;
        }
        FilterPermanent filter2 = filter.copy();
        filter2.add(new NamePredicate(cardName));
        for (Permanent land : game.getBattlefield().getActivePermanents(filter2, source.getControllerId(), game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
                    // So the ability removing has to be done before Layer 6
                    land.removeAllAbilities(source.getSourceId(), game);
                    land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
                    break;
                case AbilityAddingRemovingEffects_6:
                    land.addAbility(new AnyColorManaAbility(), source.getSourceId(), game);
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
